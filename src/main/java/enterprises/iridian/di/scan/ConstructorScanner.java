package enterprises.iridian.di.scan;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public final class ConstructorScanner implements Scanner<Constructor<?>> {

  @Override
  public List<Constructor<?>> scan(final Class<?> type) {
    final List<Constructor<?>> constructors = new ArrayList<>();

    for (final Constructor<?> constructor : type.getDeclaredConstructors()) {
      if (!constructor.isAnnotationPresent(Inject.class)) {
        continue;
      }

      constructors.add(constructor);
    }

    return constructors;
  }
}
