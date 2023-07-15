package enterprises.iridian.di.exception;

import enterprises.iridian.di.Literal;
import java.util.NoSuchElementException;

public final class NoSuchProviderException extends NoSuchElementException {

  public final Literal<?> literal;

  public NoSuchProviderException(final Literal<?> literal) {
    super(String.format("Missing provider for literal '%s'", literal));
    this.literal = literal;
  }
}
