package enterprises.iridian.di.target;

import enterprises.iridian.di.Literal;
import java.lang.reflect.Method;

public final class MethodTarget extends Target<Method> {

  MethodTarget(final Method point, final Literal<?> literal) {
    super(point, literal);
  }
}
