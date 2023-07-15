package enterprises.iridian.di.scan;

import java.util.List;

public sealed interface Scanner<T> permits ConstructorScanner, FieldScanner, MethodScanner {

  List<T> scan(final Class<?> type);
}
