package enterprises.iridian.di.target.exception;

public final class InvalidTargetPointException extends IllegalArgumentException {
  public final Object point;

  public InvalidTargetPointException(final Object point) {
    super(String.format("Invalid target point '%s'", point));
    this.point = point;
  }

  public InvalidTargetPointException(final Object point, final Exception exception) {
    super(String.format("Invalid target point '%s': %s", point, exception.getMessage()));
    this.point = point;
  }
}
