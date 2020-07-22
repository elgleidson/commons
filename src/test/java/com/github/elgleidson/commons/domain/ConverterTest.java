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

  private Converter<String, LocalDateTime> converter = Converter.converter(LocalDateTime::parse);

  @Test
  void testIsNullPredicateNull() {
    assertThatNullPointerException().isThrownBy(() -> Converter.converter(Objects::toString, null)).withMessage("predicate cannot be null");
  }

  @Test
  void testToValueFunctionNull() {
    assertThatNullPointerException().isThrownBy(() -> Converter.converter(null, Objects::isNull)).withMessage("function cannot be null");
  }

  @Test
  void testOnlyToValueFunction() {
    assertThatCode(() -> Converter.converter(Objects::toString)).doesNotThrowAnyException();
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
  void convertValueConsideredNullWithoutPredicate() {
    assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> converter.convert("  "));
  }

  @Test
  void convertValueConsideredNull() {
    converter = Converter.converter(LocalDateTime::parse, StringUtils::isNotBlank);
    assertThat(converter.convert("  ")).isNull();
  }

  @Test
  void convertValueConsideredNullWithDefault() {
    converter = Converter.converter(LocalDateTime::parse, StringUtils::isNotBlank);
    assertThat(converter.convert("  ", DEFAULT_VALUE)).isEqualTo(DEFAULT_VALUE);
  }

  @Test
  void convertValueConsideredNullWithSupplier() {
    converter = Converter.converter(LocalDateTime::parse, StringUtils::isNotBlank);
    assertThat(converter.convert("  ", DEFAULT_VALUE_SUPPLIER)).isEqualTo(DEFAULT_VALUE);
  }

  @Test
  void convertValueConsideredNullWithSupplierAsLambda() {
    converter = Converter.converter(LocalDateTime::parse, stringValue -> !stringValue.isBlank() && !stringValue.equalsIgnoreCase("null"));
    assertThat(converter.convert("null", DEFAULT_VALUE_SUPPLIER)).isEqualTo(DEFAULT_VALUE);
  }

  @Test
  void convertWithoutInvokeToValueFunction() {
    Function<String, LocalDateTime> function = mock(Function.class);

    Supplier<LocalDateTime> defaultValueSupplier = mock(Supplier.class);
    doReturn(DEFAULT_VALUE).when(defaultValueSupplier).get();

    converter = Converter.converter(function);
    assertThat(converter.convert(null, defaultValueSupplier)).isEqualTo(DEFAULT_VALUE);

    // assert that conversion function was NOT invoked and the default value supplier was invoke
    verifyNoInteractions(function);
    verify(defaultValueSupplier).get();
  }

  @Test
  void convertInvokingToValueFunction() {
    LocalDateTime expected = LocalDateTime.of(2020, 7, 22, 10, 15, 30);
    Function<String, LocalDateTime> function = mock(Function.class);
    doReturn(expected).when(function).apply(anyString());

    Supplier<LocalDateTime> defaultValueSupplier = mock(Supplier.class);

    converter = Converter.converter(function);
    assertThat(converter.convert("2020-07-22T10:15:30", defaultValueSupplier)).isEqualTo(expected);

    // assert that conversion function was invoked and the default value supplier was NOT invoke
    verify(function).apply("2020-07-22T10:15:30");
    verifyNoInteractions(defaultValueSupplier);
  }
}