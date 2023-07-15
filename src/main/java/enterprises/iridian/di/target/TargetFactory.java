package enterprises.iridian.di.target;

import enterprises.iridian.di.Literal;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class TargetFactory {

  private static final Map<Object, Target<?>> POINT_TO_TARGET_CACHE = new HashMap<>();

  private TargetFactory() {}

  public static <T> Target<?> makeTarget(final T point) {
    if (POINT_TO_TARGET_CACHE.containsKey(point)) {
      return POINT_TO_TARGET_CACHE.get(point);
    }

    if (point instanceof final Field field) {
      POINT_TO_TARGET_CACHE.put(point, new FieldTarget(field, Literal.of(field)));
    } else if (point instanceof final Constructor<?> constructor) {
      POINT_TO_TARGET_CACHE.put(point, new ConstructorTarget(constructor, Literal.of(constructor)));
    } else if (point instanceof final Method method) {
      POINT_TO_TARGET_CACHE.put(point, new MethodTarget(method, Literal.of(method)));
    } else {
      // TODO: Exception type
      throw new RuntimeException();
    }

    return POINT_TO_TARGET_CACHE.get(point);
  }
}
