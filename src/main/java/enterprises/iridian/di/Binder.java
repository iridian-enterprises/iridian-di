package enterprises.iridian.di;

import javax.inject.Provider;

public final class Binder<T> {

  private final Injector injector;
  private final Literal<T> literal;

  public Binder(final Injector injector, final Literal<T> literal) {
    this.injector = injector;
    this.literal = literal;
  }

  public void toSingleton(final T instance) {
    injector.bind(literal, () -> instance);
  }

  public void toProvider(final Provider<T> provider) {
    injector.bind(literal, provider);
  }
}
