package com.github.elgleidson.commons.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class TuplesTest {

  private static final Integer T1 = 1;
  private static final Long T2 = 2L;
  private static final Float T3 = 3.0f;
  private static final Double T4 = 4.0;
  private static final BigInteger T5 = new BigInteger("5");
  private static final BigDecimal T6 = new BigDecimal("6");
  private static final String T7 = "7";
  private static final Character T8 = '8';
  private static final Boolean T9 = Boolean.TRUE;

  @Test
  void tupleOf2() {
    Tuples.Tuple<Integer, Long> tuple = Tuples.of(T1, T2);
    assertValueAndClassEquals(T1, tuple.getT1());
    assertValueAndClassEquals(T2, tuple.getT2());
  }

  @Test
  void tupleOf3() {
    Tuples.Tuple3<Integer, Long, Float> tuple = Tuples.of(T1, T2, T3);
    assertValueAndClassEquals(T1, tuple.getT1());
    assertValueAndClassEquals(T2, tuple.getT2());
    assertValueAndClassEquals(T3, tuple.getT3());
    assertThat(tuple).isInstanceOf(Tuples.Tuple.class);
  }

  @Test
  void tupleOf4() {
    Tuples.Tuple4<Integer, Long, Float, Double> tuple = Tuples.of(T1, T2, T3, T4);
    assertValueAndClassEquals(T1, tuple.getT1());
    assertValueAndClassEquals(T2, tuple.getT2());
    assertValueAndClassEquals(T3, tuple.getT3());
    assertValueAndClassEquals(T4, tuple.getT4());
    assertThat(tuple).isInstanceOf(Tuples.Tuple3.class);
  }

  @Test
  void tupleOf5() {
    Tuples.Tuple5<Integer, Long, Float, Double, BigInteger> tuple = Tuples.of(T1, T2, T3, T4, T5);
    assertValueAndClassEquals(T1, tuple.getT1());
    assertValueAndClassEquals(T2, tuple.getT2());
    assertValueAndClassEquals(T3, tuple.getT3());
    assertValueAndClassEquals(T4, tuple.getT4());
    assertValueAndClassEquals(T5, tuple.getT5());
    assertThat(tuple).isInstanceOf(Tuples.Tuple4.class);
  }

  @Test
  void tupleOf6() {
    Tuples.Tuple6<Integer, Long, Float, Double, BigInteger, BigDecimal> tuple = Tuples.of(T1, T2, T3, T4, T5, T6);
    assertValueAndClassEquals(T1, tuple.getT1());
    assertValueAndClassEquals(T2, tuple.getT2());
    assertValueAndClassEquals(T3, tuple.getT3());
    assertValueAndClassEquals(T4, tuple.getT4());
    assertValueAndClassEquals(T5, tuple.getT5());
    assertValueAndClassEquals(T6, tuple.getT6());
    assertThat(tuple).isInstanceOf(Tuples.Tuple5.class);
  }

  @Test
  void tupleOf7() {
    Tuples.Tuple7<Integer, Long, Float, Double, BigInteger, BigDecimal, String> tuple = Tuples.of(T1, T2, T3, T4, T5, T6, T7);
    assertValueAndClassEquals(T1, tuple.getT1());
    assertValueAndClassEquals(T2, tuple.getT2());
    assertValueAndClassEquals(T3, tuple.getT3());
    assertValueAndClassEquals(T4, tuple.getT4());
    assertValueAndClassEquals(T5, tuple.getT5());
    assertValueAndClassEquals(T6, tuple.getT6());
    assertValueAndClassEquals(T7, tuple.getT7());
    assertThat(tuple).isInstanceOf(Tuples.Tuple6.class);
  }

  @Test
  void tupleOf8() {
    Tuples.Tuple8<Integer, Long, Float, Double, BigInteger, BigDecimal, String, Character> tuple = Tuples.of(T1, T2, T3, T4, T5, T6, T7, T8);
    assertValueAndClassEquals(T1, tuple.getT1());
    assertValueAndClassEquals(T2, tuple.getT2());
    assertValueAndClassEquals(T3, tuple.getT3());
    assertValueAndClassEquals(T4, tuple.getT4());
    assertValueAndClassEquals(T5, tuple.getT5());
    assertValueAndClassEquals(T6, tuple.getT6());
    assertValueAndClassEquals(T7, tuple.getT7());
    assertValueAndClassEquals(T8, tuple.getT8());
    assertThat(tuple).isInstanceOf(Tuples.Tuple7.class);
  }

  @Test
  void tupleOf9() {
    Tuples.Tuple9<Integer, Long, Float, Double, BigInteger, BigDecimal, String, Character, Boolean> tuple = Tuples.of(T1, T2, T3, T4, T5, T6, T7, T8, T9);
    assertValueAndClassEquals(T1, tuple.getT1());
    assertValueAndClassEquals(T2, tuple.getT2());
    assertValueAndClassEquals(T3, tuple.getT3());
    assertValueAndClassEquals(T4, tuple.getT4());
    assertValueAndClassEquals(T5, tuple.getT5());
    assertValueAndClassEquals(T6, tuple.getT6());
    assertValueAndClassEquals(T7, tuple.getT7());
    assertValueAndClassEquals(T8, tuple.getT8());
    assertValueAndClassEquals(T9, tuple.getT9());
    assertThat(tuple).isInstanceOf(Tuples.Tuple8.class);
  }

  private void assertValueAndClassEquals(Object expected, Object actual) {
    assertThat(actual).isEqualTo(expected);
    assertThat(actual).hasSameClassAs(expected);
  }
}