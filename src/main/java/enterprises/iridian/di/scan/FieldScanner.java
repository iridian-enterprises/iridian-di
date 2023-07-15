package enterprises.iridian.di.scan;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public final class FieldScanner implements Scanner<Field> {

  @Override
  public List<Field> scan(final Class<?> typeClass) {
    final List<Field> fields = new ArrayList<>();

    for (final Field field : typeClass.getDeclaredFields()) {
      if (!field.isAnnotationPresent(Inject.class)) {
        continue;
      }

      if (Modifier.isFinal(field.getModifiers())) {
        continue;
      }

      fields.add(field);
    }

    return fields;
  }
}
