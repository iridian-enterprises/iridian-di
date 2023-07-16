package enterprises.iridian.di;

import enterprises.iridian.di.exception.InvalidLiteralTypeException;
import enterprises.iridian.di.exception.NoSuchProviderException;
import enterprises.iridian.di.scan.ConstructorScanner;
import enterprises.iridian.di.scan.FieldScanner;
import enterprises.iridian.di.scan.MethodScanner;
import enterprises.iridian.di.scan.exception.MissingConstructorException;
import enterprises.iridian.di.target.Target;
import enterprises.iridian.di.target.TargetFactory;
import enterprises.iridian.di.target.exception.InvalidTargetPointException;

import javax.inject.Provider;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SimpleInjector implements Injector {
  private final Map<Literal<?>, Provider<?>> literalToProvider = new HashMap<>();

  private final FieldScanner fieldScanner = new FieldScanner();
  private final ConstructorScanner constructorScanner = new ConstructorScanner();
  private final MethodScanner methodScanner = new MethodScanner();

  @Override
  public void bindLoose(final Literal<?> literal, final Provider<?> provider) {
    literalToProvider.put(literal, provider);
  }

  @SuppressWarnings("unchecked")
  public <T> T create(final Class<T> typeClass) {
    final List<? extends Target<?>> constructorTargets = sortConstructors(
      constructorScanner.scan(typeClass));
    final Target<?> constructorTarget = constructorTargets.get(0);
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
      throw new NoSuchProviderException(literal);
    }

    return (Provider<T>) literalToProvider.get(literal);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T resolveBean(final Literal<?> literal) {
    try {
      final Provider<T> provider = resolveProvider(literal);
      final T bean = provider.get();
      if (bean != null) {
        return bean;
      } else {
        throw new NullPointerException();
      }
    } catch (final NoSuchProviderException providerException) {
      try {
        return (T) create(literal.typeClass);
      } catch (final MissingConstructorException |
                     InvalidLiteralTypeException |
                     InvalidTargetPointException |
                     NoSuchProviderException exception) {
        throw providerException;
      }
    }
  }

  private List<? extends Target<?>> sortConstructors(final List<Constructor<?>> constructors) {
    return constructors.stream()
      .map(TargetFactory::makeTarget)
      .sorted(this::sortByNumberOfSatisfiedBeans)
      .toList();
  }

  public int sortByNumberOfSatisfiedBeans(final Target<?> rhs, final Target<?> lhs) {
    return getNumberOfSatisfiedBeans(lhs.literal) - getNumberOfSatisfiedBeans(rhs.literal);
  }

  private int getNumberOfSatisfiedBeans(final Literal<?> literal) {
    int satisfiedBeans = 0;
    for (final Literal<?> typeParameter : literal.typeParameters) {
      if (literalToProvider.containsKey(typeParameter)) {
        satisfiedBeans++;
      } else {
        try {
          create(literal.typeClass);
          satisfiedBeans++;
        } catch (final Exception ignored) {
        }
      }
    }
    return satisfiedBeans;
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
