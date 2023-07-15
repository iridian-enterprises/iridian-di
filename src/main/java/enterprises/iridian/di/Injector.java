package enterprises.iridian.di;

import javax.inject.Provider;

public interface Injector {

  <T> void bind(final Literal<T> literal, final Provider<T> provider);

  default <T> Binder<T> bind(final Literal<T> literal) {
    return new Binder<>(this, literal);
  }

  default <T> Binder<T> bind(final Class<T> typeClass) {
    return new Binder<>(this, Literal.of(typeClass));
  }

  <T> T create(final Class<T> typeClass);

  <T> Provider<T> resolveProvider(final Literal<?> literal);

  <T> T resolveBean(final Literal<?> literal);
}
