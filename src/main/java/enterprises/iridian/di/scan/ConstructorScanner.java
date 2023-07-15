package enterprises.iridian.di.scan;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public final class ConstructorScanner implements Scanner<Constructor<?>> {

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
        // TODO: Exception type
        throw new RuntimeException(exception);
      }
    }

    return constructors;
  }
}
