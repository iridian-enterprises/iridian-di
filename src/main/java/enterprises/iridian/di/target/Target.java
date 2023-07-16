package enterprises.iridian.di.target;

import enterprises.iridian.di.Literal;

import java.util.Objects;

public abstract sealed class Target<T> permits ConstructorTarget, FieldTarget, MethodTarget {
  public final T point;
  public final Literal<?> literal;

  protected Target(final T point, final Literal<?> literal) {
    this.point = point;
    this.literal = literal;
  }

  public abstract Object inject(final Object instance, final Object... beans);

  @Override
  public final boolean equals(final Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (other instanceof final Target<?> target) {
      return Objects.equals(point, target.point) &&
        Objects.equals(literal, target.literal);
    }

    return false;
  }

  @Override
  public final int hashCode() {
    return Objects.hash(point, literal);
  }
}
