package enterprises.iridian.di.exception;

import java.lang.reflect.Type;

public final class InvalidLiteralTypeException extends IllegalArgumentException {
  public final Type type;

  public InvalidLiteralTypeException(final Type type) {
    super(String.format("Invalid literal type '%s'", type));
    this.type = type;
  }
}
