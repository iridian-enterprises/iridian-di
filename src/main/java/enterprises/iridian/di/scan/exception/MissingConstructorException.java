package enterprises.iridian.di.scan.exception;

public final class MissingConstructorException extends ScanException {

  public MissingConstructorException(final Class<?> typeClass) {
    super(String.format("Missing constructor for type '%s'", typeClass), typeClass);
  }
}
