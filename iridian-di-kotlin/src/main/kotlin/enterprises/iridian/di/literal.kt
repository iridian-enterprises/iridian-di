package enterprises.iridian.di

inline fun <reified T> literal(): Literal<T> =
  object : Literal<T>() {}
