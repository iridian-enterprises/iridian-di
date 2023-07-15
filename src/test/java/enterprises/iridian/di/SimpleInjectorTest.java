package enterprises.iridian.di;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class SimpleInjectorTest {

  private Injector injector;

  @BeforeEach
  void createNewInjector() {
    injector = new SimpleInjector();
  }

  @Test
  void resolveBeanReturnsSingletonBean() {
    final Object bean = new Object();
    injector.bind(new Literal<>() {}).toSingleton(bean);

    final Object resolvedBean = injector.resolveBean(new Literal<>() {});
    assertEquals(bean, resolvedBean);
  }

  @Test
  void resolveBeanReturnsProviderBean() {
    injector.bind(new Literal<>() {}).toProvider(Object::new);

    final Object resolvedBean = injector.resolveBean(new Literal<>() {});
    assertNotNull(resolvedBean);
  }

  @Test
  void createInjectsProperly() {
    final Foo<String> stringFoo = new Foo<>("Hi :)");
    final int integerFooValue = 5;
    final double quzValue = 4.5;
    bindBeansForBar(stringFoo, integerFooValue, quzValue);

    final Bar bar = injector.create(Bar.class);
    assertEquals(stringFoo, bar.stringFoo);

    assertEquals(integerFooValue, bar.integerFoo1.value);
    assertEquals(integerFooValue, bar.integerFoo2.value);
    assertNotEquals(bar.integerFoo1, bar.integerFoo2);

    assertEquals(quzValue, bar.quz);
  }

  void bindBeansForBar(final Foo<String> stringFoo, final int integerFooValue, final double quz) {
    injector.bind(new Literal<Foo<String>>() {}).toSingleton(stringFoo);
    injector.bind(new Literal<Foo<Integer>>() {}).toProvider(() -> new Foo<>(integerFooValue));
    injector.bind(double.class).toSingleton(quz);
  }
}
