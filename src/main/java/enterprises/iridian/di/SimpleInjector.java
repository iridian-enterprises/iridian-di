package enterprises.iridian.di;

import enterprises.iridian.di.scan.ConstructorScanner;
import enterprises.iridian.di.scan.FieldScanner;
import enterprises.iridian.di.scan.MethodScanner;
import enterprises.iridian.di.target.Target;
import enterprises.iridian.di.target.TargetFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Provider;

public final class SimpleInjector implements Injector {

  private final Map<Literal<?>, Provider<?>> literalToProvider = new HashMap<>();

  private final FieldScanner fieldScanner = new FieldScanner();
  private final ConstructorScanner constructorScanner = new ConstructorScanner();
  private final MethodScanner methodScanner = new MethodScanner();

  public <T> void bind(final Literal<T> literal, final Provider<T> provider) {
    literalToProvider.put(literal, provider);
  }

  @SuppressWarnings("unchecked")
  public <T> T create(final Class<T> typeClass) {
    final List<Constructor<?>> constructors = constructorScanner.scan(typeClass);
    // TODO: Find "best" constructor, sort by number of beans satisfied
    final Constructor<?> constructor = constructors.get(0);
    final Target<?> constructorTarget = TargetFactory.makeTarget(constructor);
    final Object[] constructorBeans = resolveBeans(constructorTarget.literal);
    final Object instance = constructorTarget.inject(null, constructorBeans);

    final List<Field> fields = fieldScanner.scan(typeClass);
    for (final Field field : fields) {
      final Target<?> fieldTarget = TargetFactory.makeTarget(field);
      final Object fieldBean = resolveBean(fieldTarget.literal);
      fieldTarget.inject(instance, fieldBean);
    }

    final List<Method> methods = methodScanner.scan(typeClass);
    for (final Method method : methods) {
      final Target<?> methodTarget = TargetFactory.makeTarget(method);
      final Object[] methodBeans = resolveBeans(methodTarget.literal);
      methodTarget.inject(instance, methodBeans);
    }

    return (T) instance;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> Provider<T> resolveProvider(final Literal<?> literal) {
    if (!literalToProvider.containsKey(literal)) {
      // TODO: Exception type
      throw new RuntimeException();
    }

    return (Provider<T>) literalToProvider.get(literal);
  }

  private Object[] resolveBeans(final Literal<?> literal) {
    final Literal<?>[] typeParameters = literal.typeParameters;
    final Object[] beans = new Object[typeParameters.length];
    for (int i = 0; i < typeParameters.length; ++i) {
      beans[i] = resolveBean(typeParameters[i]);
    }

    return beans;
  }
}
