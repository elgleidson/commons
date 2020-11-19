package com.github.elgleidson.commons.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

class ConverterTest {

  private static final LocalDateTime DEFAULT_VALUE = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
  private static final Supplier<LocalDateTime> DEFAULT_VALUE_SUPPLIER = () -> DEFAULT_VALUE;

  private Converter<String, LocalDateTime> converter = Converter.create(LocalDateTime::parse);

  @Test
  void createWithPredicateNullShouldThrowException() {
    assertThatNullPointerException().isThrownBy(() -> Converter.create(Objects::toString, null)).withMessage("predicate cannot be null");
  }

  @Test
  void createWithMapperNullShouldThrowException() {
    assertThatNullPointerException().isThrownBy(() -> Converter.create(null, Objects::isNull)).withMessage("mapper cannot be null");
  }

  @Test
  void createWithOnlyMapperShouldNotThrowException() {
    assertThatCode(() -> Converter.create(Objects::toString)).doesNotThrowAnyException();
  }

  @Test
  void convert() {
    assertThat(converter.convert("2020-07-22T10:15:30")).isEqualTo(LocalDateTime.of(2020, 7, 22, 10, 15, 30));
  }

  @Test
  void convertNull() {
    assertThat(converter.convert(null)).isNull();
  }

  @Test
  void convertNullWithDefault() {
    assertThat(converter.convert(null, DEFAULT_VALUE)).isEqualTo(DEFAULT_VALUE);
  }

  @Test
  void convertNullWithSupplier() {
    assertThat(converter.convert(null, DEFAULT_VALUE_SUPPLIER)).isEqualTo(DEFAULT_VALUE);
  }

  @Test
  void convertValueConsideredInconvertibleWithoutPredicate() {
    assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> converter.convert("  "));
  }

  @Test
  void convertValueConsideredInconvertible() {
    converter = Converter.create(LocalDateTime::parse, StringUtils::isNotBlank);
    assertThat(converter.convert("  ")).isNull();
  }

  @Test
  void convertValueConsideredInconvertibleWithDefault() {
    converter = Converter.create(LocalDateTime::parse, StringUtils::isNotBlank);
    assertThat(converter.convert("  ", DEFAULT_VALUE)).isEqualTo(DEFAULT_VALUE);
  }

  @Test
  void convertValueConsideredInconvertibleWithSupplier() {
    converter = Converter.create(LocalDateTime::parse, StringUtils::isNotBlank);
    assertThat(converter.convert("  ", DEFAULT_VALUE_SUPPLIER)).isEqualTo(DEFAULT_VALUE);
  }

  @Test
  void convertValueConsideredInconvertibleWithSupplierAsLambda() {
    converter = Converter.create(LocalDateTime::parse, stringValue -> !stringValue.isBlank() && !stringValue.equalsIgnoreCase("null"));
    assertThat(converter.convert("null", DEFAULT_VALUE_SUPPLIER)).isEqualTo(DEFAULT_VALUE);
  }

  @Test
  void convertWithoutInvokeMapper() {
    Function<String, LocalDateTime> mapper = mock(Function.class);

    Supplier<LocalDateTime> defaultValueSupplier = mock(Supplier.class);
    doReturn(DEFAULT_VALUE).when(defaultValueSupplier).get();

    converter = Converter.create(mapper);
    assertThat(converter.convert(null, defaultValueSupplier)).isEqualTo(DEFAULT_VALUE);

    // assert that the mapper was NOT invoked and the default value supplier was invoke instead
    verifyNoInteractions(mapper);
    verify(defaultValueSupplier).get();
  }

  @Test
  void convertInvokingToValueFunction() {
    LocalDateTime expected = LocalDateTime.of(2020, 7, 22, 10, 15, 30);
    Function<String, LocalDateTime> mapper = mock(Function.class);
    doReturn(expected).when(mapper).apply(anyString());

    Supplier<LocalDateTime> defaultValueSupplier = mock(Supplier.class);

    converter = Converter.create(mapper);
    assertThat(converter.convert("2020-07-22T10:15:30", defaultValueSupplier)).isEqualTo(expected);

    // assert that the mapper was invoked and the default value supplier was NOT invoke
    verify(mapper).apply("2020-07-22T10:15:30");
    verifyNoInteractions(defaultValueSupplier);
  }
}