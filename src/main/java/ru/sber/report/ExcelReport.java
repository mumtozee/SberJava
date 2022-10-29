package ru.sber.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

public class ExcelReport implements Report {

  private final Workbook book;
  private final Sheet mainSheet;
  private final Map<String, Integer> indexOfField;

  public ExcelReport(String reportName, List<String> header) {
    book = new XSSFWorkbook();
    mainSheet = book.createSheet(reportName);
    indexOfField = getFieldIndices(header);
    addHeader(header);
  }

  private @NotNull Map<String, Integer> getFieldIndices(List<String> fields) {
    Map<String, Integer> indices = new HashMap<>();
    Collections.sort(fields);
    for (int i = 0; i < fields.size(); ++i) {
      indices.put(fields.get(i), i);
    }
    return indices;
  }

  private void addHeader(@NotNull List<String> header) {
    Map<String, String> tmpMap = new HashMap<>();
    for (String item : header) {
      tmpMap.put(item, item);
    }
    addRow(tmpMap);
  }

  public void addRow(@NotNull Map<String, String> objectMap) {
    Row newRow = mainSheet.createRow(mainSheet.getLastRowNum() + 1);
    for (String key : objectMap.keySet()) {
      Cell cell = newRow.createCell(indexOfField.get(key));
      cell.setCellValue(objectMap.get(key));
    }
  }

  @Override
  public byte[] asBytes() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeTo(baos);
    return baos.toByteArray();
  }

  @Override
  public void writeTo(OutputStream os) {
    try {
      book.write(os);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
