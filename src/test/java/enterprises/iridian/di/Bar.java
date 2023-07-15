package enterprises.iridian.di;

import java.util.List;

final class Bar {

  final Foo<String> stringFoo;

  Bar(final Foo<String> stringFoo) {
    this.stringFoo = stringFoo;
  }

  List<Double> baz(final int x, final int y) {
    return List.of();
  }
}
