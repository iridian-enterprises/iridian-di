package enterprises.iridian.di.target;

import enterprises.iridian.di.Literal;
import enterprises.iridian.di.target.exception.InvalidTargetPointException;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

public final class FieldTarget extends Target<Field> {

  FieldTarget(final Field point, final Literal<?> literal) {
    super(point, literal);
  }

  @Override
  public Object inject(final Object instance, final Object... beans) {
    final Object bean = beans[0];

    try {
      point.setAccessible(true);
      point.set(instance, bean);
    } catch (final InaccessibleObjectException |
                   SecurityException |
                   IllegalAccessException exception) {
      throw new InvalidTargetPointException(point, exception);
    }

    return instance;
  }
}
