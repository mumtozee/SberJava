package ru.sber.report.util;

import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;

public class FieldExtractor {
  public static Object extract(@NotNull Object o, String fieldName) {
    Object result;
    try {
      Field field = o.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      result = field.get(o);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
    return result;
  }
}
