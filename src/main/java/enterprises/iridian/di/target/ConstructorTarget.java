package enterprises.iridian.di.target;

import enterprises.iridian.di.Literal;
import java.lang.reflect.Constructor;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;

public final class ConstructorTarget extends Target<Constructor<?>> {

  ConstructorTarget(final Constructor<?> point, final Literal<?> literal) {
    super(point, literal);
  }

  @Override
  public Object inject(final Object instance, final Object... beans) {
    try {
      point.setAccessible(true);
      return point.newInstance(beans);
    } catch (final InaccessibleObjectException |
                   SecurityException |
                   InstantiationException |
                   IllegalAccessException |
                   InvocationTargetException exception) {
      // TODO: Exception type
      throw new RuntimeException(exception);
    }
  }
}
