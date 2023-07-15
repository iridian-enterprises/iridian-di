package enterprises.iridian.di;

import javax.inject.Inject;

final class Bar {

  final Foo<String> stringFoo;

  @Inject
  Foo<Integer> integerFoo1;

  @Inject
  Foo<Integer> integerFoo2;

  double quz;

  @Inject
  Bar(final Foo<String> stringFoo) {
    this.stringFoo = stringFoo;
  }

  @Inject
  void baz(final double quz) {
    this.quz = quz;
  }
}
