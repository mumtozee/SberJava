package ru.sber.report;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import ru.sber.garage.ReportFieldName;

public class ExcelReportGenerator<T> implements ReportGenerator<T> {

  @Override
  public ExcelReport generate(@NotNull List<T> entities) {
    Map<String, String> fieldMapping = getFieldMapping(entities.get(0));
    List<String> header = new ArrayList<>(fieldMapping.values());
    ExcelReport report = new ExcelReport(entities.get(0).getClass().getName(), header);
    for (T entity : entities) {
      Map<String, String> entityMap = new HashMap<>();
      List<Field> allFields = getAllFields(entity);
      for (Field field : allFields) {
        field.setAccessible(true);
        String value = "";
        try {
          value = field.get(entity).toString();
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e.getMessage());
        }
        entityMap.put(fieldMapping.get(field.getName()), value);
      }
      report.addRow(entityMap);
    }
    return report;
  }

  private @NotNull Map<String, String> getFieldMapping(@NotNull T object) {
    Map<String, String> fieldMapping = new HashMap<>();
    Class<?> clazz = object.getClass();
    while (!clazz.equals(Object.class)) {
      Field[] fields = clazz.getDeclaredFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(ReportFieldName.class)) {
          fieldMapping.put(field.getName(), field.getAnnotation(ReportFieldName.class).value());
        } else {
          fieldMapping.put(field.getName(), field.getName());
        }
      }
      clazz = clazz.getSuperclass();
    }
    return fieldMapping;
  }

  private @NotNull List<Field> getAllFields(@NotNull T object) {
    Class<?> clazz = object.getClass();
    List<Field> allFields = new ArrayList<>();
    while (!clazz.equals(Object.class)) {
      allFields.addAll(List.of(clazz.getDeclaredFields()));
      clazz = clazz.getSuperclass();
    }
    return allFields;
  }
}
