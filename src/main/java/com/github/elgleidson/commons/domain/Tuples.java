package com.github.elgleidson.commons.domain;

public interface Tuples {

  class Tuple<T1, T2> {
    private final T1 t1;
    private final T2 t2;

    private Tuple(T1 t1, T2 t2) {
      this.t1 = t1;
      this.t2 = t2;
    }

    public T1 getT1() {
      return t1;
    }

    public T2 getT2() {
      return t2;
    }
  }

  class Tuple3<T1,T2,T3> extends Tuple<T1,T2> {
    private final T3 t3;

    private Tuple3(T1 t1, T2 t2, T3 t3) {
      super(t1, t2);
      this.t3 = t3;
    }

    public T3 getT3() {
      return t3;
    }
  }

  class Tuple4<T1,T2,T3,T4> extends Tuple3<T1,T2,T3> {
    private final T4 t4;

    private Tuple4(T1 t1, T2 t2, T3 t3, T4 t4) {
      super(t1, t2, t3);
      this.t4 = t4;
    }

    public T4 getT4() {
      return t4;
    }
  }

  class Tuple5<T1,T2,T3,T4,T5> extends Tuple4<T1,T2,T3,T4> {
    private final T5 t5;

    private Tuple5(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      super(t1, t2, t3, t4);
      this.t5 = t5;
    }

    public T5 getT5() {
      return t5;
    }
  }

  class Tuple6<T1,T2,T3,T4,T5,T6> extends Tuple5<T1,T2,T3,T4,T5> {
    private final T6 t6;

    private Tuple6(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      super(t1, t2, t3, t4, t5);
      this.t6 = t6;
    }

    public T6 getT6() {
      return t6;
    }
  }

  class Tuple7<T1,T2,T3,T4,T5,T6,T7> extends Tuple6<T1,T2,T3,T4,T5,T6> {
    private final T7 t7;

    private Tuple7(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      super(t1, t2, t3, t4, t5, t6);
      this.t7 = t7;
    }

    public T7 getT7() {
      return t7;
    }
  }

  class Tuple8<T1,T2,T3,T4,T5,T6,T7,T8> extends Tuple7<T1,T2,T3,T4,T5,T6,T7> {
    private final T8 t8;

    private Tuple8(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      super(t1, t2, t3, t4, t5, t6, t7);
      this.t8 = t8;
    }

    public T8 getT8() {
      return t8;
    }
  }

  class Tuple9<T1,T2,T3,T4,T5,T6,T7,T8,T9> extends Tuple8<T1,T2,T3,T4,T5,T6,T7,T8> {
    private final T9 t9;

    private Tuple9(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      super(t1, t2, t3, t4, t5, t6, t7, t8);
      this.t9 = t9;
    }

    public T9 getT9() {
      return t9;
    }
  }


  static <T1, T2> Tuple<T1, T2> of(T1 t1, T2 t2) {
    return new Tuple<>(t1, t2);
  }

  static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
    return new Tuple3<>(t1, t2, t3);
  }

  static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> of(T1 t1, T2 t2, T3 t3, T4 t4) {
    return new Tuple4<>(t1, t2, t3, t4);
  }

  static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
    return new Tuple5<>(t1, t2, t3, t4, t5);
  }

  static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
    return new Tuple6<>(t1, t2, t3, t4, t5, t6);
  }

  static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
    return new Tuple7<>(t1, t2, t3, t4, t5, t6, t7);
  }

  static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
    return new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8);
  }

  static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
    return new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9);
  }
}
