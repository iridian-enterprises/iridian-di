package enterprises.iridian.di.scan.exception;

public abstract class ScanException extends RuntimeException {

  public final Class<?> typeClass;

  protected ScanException(final String message, final Class<?> typeClass) {
    super(message);
    this.typeClass = typeClass;
  }
}
