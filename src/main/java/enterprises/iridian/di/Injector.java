package enterprises.iridian.di;

import javax.inject.Provider;
import java.util.Arrays;
import java.util.List;
import javax.inject.Provider;

public interface Injector {

  void bindLoose(final Literal<?> literal, final Provider<?> provider);

  <T> T create(final Class<T> typeClass);

  <T> Provider<T> resolveProvider(final Literal<?> literal);

  <T> T resolveBean(final Literal<?> literal);

  default <T> void bind(final Literal<T> literal, final Provider<T> provider) {
    bindLoose(literal, provider);
  }

  default <T> Binder<T> bind(final Literal<T> literal) {
    return new Binder<>(this, literal);
  }

  default <T> Binder<T> bind(final Class<T> typeClass) {
    return bind(Literal.of(typeClass));
  }

  default void bindModule(final Module module) {
    module.bind(this);
  }

  default void bindModules(final Module... modules) {
    bindModules(Arrays.asList(modules));
  }

  default void bindModules(final List<Module> modules) {
    for (final Module module : modules) {
      bindModule(module);
    }
  }
}
