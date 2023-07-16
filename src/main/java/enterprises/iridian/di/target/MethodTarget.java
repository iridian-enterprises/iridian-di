package enterprises.iridian.di.target;

import enterprises.iridian.di.Literal;
import enterprises.iridian.di.target.exception.InvalidTargetPointException;

import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class MethodTarget extends Target<Method> {

  MethodTarget(final Method point, final Literal<?> literal) {
    super(point, literal);
  }

  @Override
  public Object inject(final Object instance, final Object... beans) {
    try {
      point.setAccessible(true);
      point.invoke(instance, beans);
    } catch (final InaccessibleObjectException |
                   SecurityException |
                   IllegalAccessException |
                   InvocationTargetException exception) {
      throw new InvalidTargetPointException(point, exception);
    }

    return instance;
  }
}
