package enterprises.iridian.di.target;

import enterprises.iridian.di.Literal;
import java.lang.reflect.Field;

public final class FieldTarget extends Target<Field> {

  FieldTarget(final Field point, final Literal<?> literal) {
    super(point, literal);
  }
}
