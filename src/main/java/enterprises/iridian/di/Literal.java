package enterprises.iridian.di;

import enterprises.iridian.di.exception.InvalidLiteralTypeException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Objects;

public abstract class Literal<T> {

  public final Type type;
  public final Class<T> typeClass;
  public final Literal<?>[] typeParameters;

  public Literal() {
    final Type superClass = getClass().getGenericSuperclass();
    final Type superType = resolveSuperType(superClass);

    this.type = superType;
    this.typeClass = resolveTypeClass(superType);
    this.typeParameters = resolveTypeParameters(superType);
  }

  private Literal(final Type type) {
    this.type = type;
    this.typeClass = resolveTypeClass(type);
    this.typeParameters = resolveTypeParameters(type);
  }

  private Literal(final Class<T> type) {
    this.type = type;
    this.typeClass = type;
    this.typeParameters = new Literal[0];
  }

  private Literal(final Field field) {
    final Type type = field.getGenericType();

    this.type = type;
    this.typeClass = resolveTypeClass(type);
    this.typeParameters = resolveTypeParameters(type);
  }

  @SuppressWarnings("unchecked")
  private Literal(final Constructor<?> constructor) {
    final Type type = constructor.getDeclaringClass();

    this.type = type;
    this.typeClass = (Class<T>) type;
    this.typeParameters = resolveTypeParameters(constructor);
  }

  private Literal(final Method method) {
    final Type type = method.getGenericReturnType();

    this.type = type;
    this.typeClass = resolveTypeClass(type);
    this.typeParameters = resolveTypeParameters(method);
  }

  public static <T> Literal<T> of(final Class<T> type) {
    return new Literal<>(type) {};
  }

  public static Literal<?> of(final Field field) {
    return new Literal<>(field) {};
  }

  public static Literal<?> of(final Constructor<?> constructor) {
    return new Literal<>(constructor) {};
  }

  public static Literal<?> of(final Method method) {
    return new Literal<>(method) {};
  }

  private static Type resolveSuperType(final Type type) {
    if (type instanceof Class<?>) {
      return type;
    } else if (type instanceof final ParameterizedType parameterizedType) {
      return parameterizedType.getActualTypeArguments()[0];
    }

    throw new InvalidLiteralTypeException(type);
  }

  @SuppressWarnings("unchecked")
  private static <T> Class<T> resolveTypeClass(final Type type) {
    if (type instanceof Class<?>) {
      return (Class<T>) type;
    } else if (type instanceof final ParameterizedType parameterizedType) {
      return (Class<T>) parameterizedType.getRawType();
    } else if (type instanceof final WildcardType wildcardType) {
      return (Class<T>) wildcardType.getUpperBounds()[0];
    }

    throw new InvalidLiteralTypeException(type);
  }

  private static Literal<?>[] resolveTypeParameters(final Type type) {
    if (type instanceof Class<?>) {
      return new Literal[0];
    } else if (type instanceof final ParameterizedType parameterizedType) {
      final Type[] arguments = parameterizedType.getActualTypeArguments();
      final int length = arguments.length;
      final Literal<?>[] literals = new Literal<?>[length];
      for (int i = 0; i < length; ++i) {
        literals[i] = new Literal<>(arguments[i]) {};
      }
      return literals;
    }

    return new Literal[0];
  }

  private static Literal<?>[] resolveTypeParameters(final Constructor<?> constructor) {
    return resolveTypeParameters(constructor.getParameters());
  }

  private static Literal<?>[] resolveTypeParameters(final Method method) {
    return resolveTypeParameters(method.getParameters());
  }

  private static Literal<?>[] resolveTypeParameters(final Parameter[] parameters) {
    final Literal<?>[] literals = new Literal<?>[parameters.length];
    for (int i = 0; i < parameters.length; ++i) {
      literals[i] = new Literal<>(parameters[i].getParameterizedType()) {};
    }
    return literals;
  }

  @Override
  public final boolean equals(final Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (other instanceof final Literal<?> literal) {
      return Objects.equals(type, literal.type) &&
          Objects.equals(typeClass, literal.typeClass) &&
          Arrays.equals(typeParameters, literal.typeParameters);
    }

    return false;
  }

  @Override
  public final int hashCode() {
    int result = Objects.hash(type, typeClass);
    result = 31 * result + Arrays.hashCode(typeParameters);
    return result;
  }
}
