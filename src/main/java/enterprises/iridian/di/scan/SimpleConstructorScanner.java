package enterprises.iridian.di.scan;

import enterprises.iridian.di.scan.exception.MissingConstructorException;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public final class SimpleConstructorScanner implements ConstructorScanner {

  @Override
  public List<Constructor<?>> scan(final Class<?> typeClass) {
    final List<Constructor<?>> constructors = new ArrayList<>();

    for (final Constructor<?> constructor : typeClass.getDeclaredConstructors()) {
      if (!constructor.isAnnotationPresent(Inject.class)) {
        continue;
      }

      constructors.add(constructor);
    }

    if (constructors.isEmpty()) {
      try {
        final Constructor<?> constructor = typeClass.getDeclaredConstructor();
        constructors.add(constructor);
      } catch (final NoSuchMethodException exception) {
        throw new MissingConstructorException(typeClass);
      }
    }

    return constructors;
  }
}
