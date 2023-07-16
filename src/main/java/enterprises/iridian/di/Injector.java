package enterprises.iridian.di;

import javax.inject.Provider;
import java.util.List;

public interface Injector {

  default void bind(final Module... modules) {
    for (final Module module : modules) {
      bind(module);
    }
  }

  default void bind(final List<Module> modules) {
    for (final Module module : modules) {
      bind(module);
    }
  }

  default void bind(final Module module) {
    module.bind(this);
  }

  default <T> void bind(final Literal<T> literal, final Provider<T> provider) {
    bindLoose(literal, provider);
  }

  void bindLoose(final Literal<?> literal, final Provider<?> provider);

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
