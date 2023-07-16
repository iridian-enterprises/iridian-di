package enterprises.iridian.di;

import javax.inject.Inject;
import java.util.List;

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

  List<Integer> baz(final int x, final int y) {
    return List.of();
  }

  @Inject
  void quz(final double quz) {
    this.quz = quz;
  }
}
