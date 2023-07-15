package enterprises.iridian.di;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import org.junit.jupiter.api.Test;

final class LiteralTest {

  @Test
  void inheritanceConstructorResolvesType() {
    final Literal<Queue<Integer>> literal = new Literal<>() {};
    // TODO: Check equals against real type
    assertNotNull(literal.type);
  }

  @Test
  void inheritanceConstructorResolvesTypeClass() {
    final Literal<Stack<String>> literal = new Literal<>() {};
    assertEquals(Stack.class, literal.typeClass);
  }

  @Test
  void inheritanceConstructorResolvesParameters() {
    final Literal<Map<String, List<Integer>>> literal = new Literal<>() {};
    assertEquals(Map.class, literal.typeClass);
    assertEquals(String.class, literal.typeParameters[0].typeClass);
    assertEquals(List.class, literal.typeParameters[1].typeClass);
    assertEquals(Integer.class, literal.typeParameters[1].typeParameters[0].typeClass);
  }

  @Test
  void fieldConstructorResolvesType() throws NoSuchFieldException {
    final Field field = Bar.class.getDeclaredField("stringFoo");
    assertNotNull(field);

    final Literal<?> literal = Literal.of(field);
    // TODO: Check equals against real type
    assertNotNull(literal.type);
  }

  @Test
  void fieldConstructorResolvesTypeClass() throws NoSuchFieldException {
    final Field field = Bar.class.getDeclaredField("stringFoo");
    assertNotNull(field);

    final Literal<?> literal = Literal.of(field);
    assertEquals(Foo.class, literal.typeClass);
  }

  @Test
  void fieldConstructorResolvesTypeParameters() throws NoSuchFieldException {
    final Field field = Bar.class.getDeclaredField("stringFoo");
    assertNotNull(field);

    final Literal<?> literal = Literal.of(field);
    assertEquals(String.class, literal.typeParameters[0].typeClass);
  }

  @Test
  void constructorConstructorResolvesType() throws NoSuchMethodException {
    final Constructor<?> constructor = Bar.class.getDeclaredConstructor(Foo.class);
    assertNotNull(constructor);

    final Literal<?> literal = Literal.of(constructor);
    // TODO: Check equals against real type
    assertNotNull(literal.type);
  }

  @Test
  void constructorConstructorResolvesTypeClass() throws NoSuchMethodException {
    final Constructor<?> constructor = Bar.class.getDeclaredConstructor(Foo.class);
    assertNotNull(constructor);

    final Literal<?> literal = Literal.of(constructor);
    assertEquals(Bar.class, literal.typeClass);
  }

  @Test
  void constructorConstructorResolvesTypeParameters() throws NoSuchMethodException {
    final Constructor<?> constructor = Bar.class.getDeclaredConstructor(Foo.class);
    assertNotNull(constructor);

    final Literal<?> literal = Literal.of(constructor);
    assertEquals(Foo.class, literal.typeParameters[0].typeClass);
  }

  @Test
  void methodConstructorResolvesType() throws NoSuchMethodException {
    final Method method = Bar.class.getDeclaredMethod("baz", int.class, int.class);
    assertNotNull(method);

    final Literal<?> literal = Literal.of(method);
    // TODO: Check equals against real type
    assertNotNull(literal.type);
  }

  @Test
  void methodConstructorResolvesTypeClass() throws NoSuchMethodException {
    final Method method = Bar.class.getDeclaredMethod("baz", int.class, int.class);
    assertNotNull(method);

    final Literal<?> literal = Literal.of(method);
    assertEquals(List.class, literal.typeClass);
  }

  @Test
  void methodConstructorResolvesTypeParameters() throws NoSuchMethodException {
    final Method method = Bar.class.getDeclaredMethod("baz", int.class, int.class);
    assertNotNull(method);

    final Literal<?> literal = Literal.of(method);
    assertEquals(int.class, literal.typeParameters[0].typeClass);
    assertEquals(int.class, literal.typeParameters[1].typeClass);
  }
}