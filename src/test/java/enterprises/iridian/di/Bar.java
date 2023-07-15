package enterprises.iridian.di;

import java.util.List;
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

  List<Integer> baz(final int x, final int y) {
    return List.of();
  }

  @Inject
  void quz(final double quz) {
    this.quz = quz;
  }
}
