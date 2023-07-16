package enterprises.iridian.di.scan;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class SimpleMethodScanner implements MethodScanner {

  @Override
  public List<Method> scan(final Class<?> typeClass) {
    final List<Method> methods = new ArrayList<>();

    for (final Method method : typeClass.getDeclaredMethods()) {
      if (!method.isAnnotationPresent(Inject.class)) {
        continue;
      }

      methods.add(method);
    }

    return methods;
  }
}
