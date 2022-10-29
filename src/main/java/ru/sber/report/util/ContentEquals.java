package ru.sber.report.util;

import org.jetbrains.annotations.Contract;

public interface ContentEquals {

  @Contract(value = "_, null -> false", pure = true)
  static boolean check(Object lhs, Object rhs) {
    return lhs.equals(rhs);
  }
}
