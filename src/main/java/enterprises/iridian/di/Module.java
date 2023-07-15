package enterprises.iridian.di;

@FunctionalInterface
public interface Module {

  void bind(final Injector injector);
}
