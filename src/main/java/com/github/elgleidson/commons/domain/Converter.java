package com.github.elgleidson.commons.domain;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Converter<F,T> {

  /**
   * Convert a value from a type to another
   * @param from value to be converted
   * @return the converted value or null if <code>from</code> is null
   */
  default T convert(final F from) {
    return this.convert(from, () -> null);
  }

  /**
   * Convert a value from a type to another
   * @param from value to be converted
   * @param defaultValue default value if <code>from</code> is null
   * @return the converted value or the <code>defaultValue</code> if <code>from</code> is null
   */
  default T convert(final F from, final T defaultValue) {
    Objects.requireNonNull(defaultValue, "defaultValue cannot be null");
    return this.convert(from, () -> defaultValue);
  }

  /**
   * Convert a value from a type to another
   * @param from value to be converted
   * @param defaultValueSupplier default value supplier if <code>from</code> is null
   * @return the converted value or the <code>defaultValueSupplier.get()</code> if <code>from</code> is null
   */
  default T convert(final F from, final Supplier<T> defaultValueSupplier) {
    Objects.requireNonNull(defaultValueSupplier, "defaultValueSupplier cannot be null");
    return this.isConvertible(from) ? this.convertNullSafe(from) : defaultValueSupplier.get();
  }

  default boolean isConvertible(final F from) {
    return from != null;
  }

  T convertNullSafe(final F from);

  /**
   * Creates a converter
   * @param function function that converts a value from a type to another
   * @return a converter that checks if the value to be converted is not null and then calls <code>function</code>
   */
  static <F,T> Converter<F,T> converter(final Function<F,T> function) {
    return converter(function, Objects::nonNull);
  }

  /**
   * Creates a converter
   * @param function function that converts a value from a type to another
   * @param predicate predicate to be tested against the value being converted
   * @return a converter that checks if the <code>predicate</code> is true and then calls <code>function</code>
   */
  static <F,T> Converter<F,T> converter(final Function<F,T> function, final Predicate<F> predicate) {
    Objects.requireNonNull(function, "function cannot be null");
    Objects.requireNonNull(predicate, "predicate cannot be null");

    return new Converter<>() {
      @Override
      public T convertNullSafe(F from) {
        return function.apply(from);
      }

      @Override
      public boolean isConvertible(F from) {
        return predicate.test(from);
      }
    };
  }

}
