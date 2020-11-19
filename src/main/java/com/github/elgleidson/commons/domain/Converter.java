package com.github.elgleidson.commons.domain;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Converter<F,T> {

  /**
   * Convert a value from a type to another.
   * @param from value to be converted.
   * @return the converted value if {@code from} {@link #isConvertible} or {@code null} otherwise.
   */
  default T convert(final F from) {
    return this.convert(from, () -> null);
  }

  /**
   * Convert a value from a type to another.
   * @param from value to be converted.
   * @param defaultValue default value if {@code from} is not {@link #isConvertible}.
   * @return the converted value if {@code from} {@link #isConvertible} or {@code defaultValue} otherwise.
   */
  default T convert(final F from, final T defaultValue) {
    Objects.requireNonNull(defaultValue, "defaultValue cannot be null");
    return this.convert(from, () -> defaultValue);
  }

  /**
   * Convert a value from a type to another.
   * @param from value to be converted.
   * @param defaultValueSupplier default value supplier if {@code from} is not {@link #isConvertible}.
   * @return the converted value if {@code from} {@link #isConvertible} or {@link Supplier#get} otherwise.
   */
  default T convert(final F from, final Supplier<T> defaultValueSupplier) {
    Objects.requireNonNull(defaultValueSupplier, "defaultValueSupplier cannot be null");
    return this.isConvertible(from) ? this.convertNullSafe(from) : defaultValueSupplier.get();
  }

  /**
   * Check if {@code from} is convertible before converting.
   * The default implementation checks for {@code null}.
   * @param from value to be checked.
   * @return {@code true} if {@code from} is convertible.
   */
  default boolean isConvertible(final F from) {
    return from != null;
  }

  /**
   * Convert a value from a type to another.
   * The value passed to this function should never be {@code null}.
   * The value returned by this function should never be {@code null}.
   * @param from a non-null value to be converted.
   * @return the non-null converted value.
   */
  T convertNullSafe(final F from);

  /**
   * Creates a converter.
   * @param mapper function that converts a value from a type to another.
   * @return a converter that implements {@link #isConvertible} using {@link Objects#nonNull} and {@link #convertNullSafe} using {@link Function#apply}.
   */
  static <F,T> Converter<F,T> create(final Function<F,T> mapper) {
    return create(mapper, Objects::nonNull);
  }

  /**
   * Creates a converter.
   * @param mapper function that converts a value from a type to another.
   * @param predicate predicate to be used to test if the value {@link #isConvertible}.
   * @return a converter that implements {@link #isConvertible} using {@link Predicate#test} and {@link #convertNullSafe} using {@link Function#apply}.
   */
  static <F,T> Converter<F,T> create(final Function<F,T> mapper, final Predicate<F> predicate) {
    Objects.requireNonNull(mapper, "mapper cannot be null");
    Objects.requireNonNull(predicate, "predicate cannot be null");

    return new Converter<>() {
      @Override
      public T convertNullSafe(F from) {
        return mapper.apply(from);
      }

      @Override
      public boolean isConvertible(F from) {
        return predicate.test(from);
      }
    };
  }

}
