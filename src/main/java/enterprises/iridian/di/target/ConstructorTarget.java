package enterprises.iridian.di.target;

import enterprises.iridian.di.Literal;
import java.lang.reflect.Constructor;

public final class ConstructorTarget extends Target<Constructor<?>> {

  ConstructorTarget(final Constructor<?> point, final Literal<?> literal) {
    super(point, literal);
  }
}
