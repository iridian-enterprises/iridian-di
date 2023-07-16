package enterprises.iridian.di;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.inject.Singleton;

public abstract class Module {

  final void bind(final Injector injector) {
    for (final Method method : getClass().getDeclaredMethods()) {
      if (!method.isAnnotationPresent(Provides.class)) {
        continue;
      }

      final Literal<?> literal = Literal.of(method.getGenericReturnType());
      if (method.isAnnotationPresent(Singleton.class)) {
        bindSingleton(injector, method, literal);
      } else {
        bind(injector, method, literal);
      }
    }
  }

  private void bindSingleton(final Injector injector, final Method method,
      final Literal<?> literal) {
    final Object instance;
    try {
      instance = method.invoke(this);
    } catch (final IllegalAccessException | InvocationTargetException exception) {
      throw new RuntimeException(exception);
    }

    injector.bindLoose(literal, () -> instance);
  }

  private void bind(final Injector injector, final Method method, final Literal<?> literal) {
    injector.bindLoose(literal, () -> {
      try {
        return method.invoke(this);
      } catch (final IllegalAccessException | InvocationTargetException exception) {
        throw new RuntimeException(exception);
      }
    });
  }
}
