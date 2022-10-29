package ru.sber.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReportAnalyticsDecorator implements Report {
  private final ExcelReport report;
  private final Map<String, String> queries;

  public ExcelReportAnalyticsDecorator(ExcelReport report) {
    this.report = report;
    queries = new HashMap<>();
  }

  public void addRow(Map<String, String> objectMap) {
    report.addRow(objectMap);
  }

  public void addAnalytics(String queryName, Object queryResult) {
    if (queryResult == null) {
      queryResult = "null";
    }
    queries.put(queryName, queryResult.toString());
  }

  private Workbook createWorkbookWithAnalytics() {
    Workbook withAnalytics;
    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(report.asBytes());
      withAnalytics = new XSSFWorkbook(bais);
      bais.close();
      Sheet analyticsSheet = withAnalytics.createSheet("Analytics");
      int rowIdx = 0;
      for (String queryName : queries.keySet()) {
        Row newAnalyticsRow = analyticsSheet.createRow(rowIdx);
        Cell queryNameCell = newAnalyticsRow.createCell(0);
        queryNameCell.setCellValue(queryName);
        Cell queryResultCell = newAnalyticsRow.createCell(1);
        queryResultCell.setCellValue(queries.get(queryName));
        rowIdx += 2;
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
    return withAnalytics;
  }

  @Override
  public byte[] asBytes() {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    writeTo(bos);
    return bos.toByteArray();
  }

  @Override
  public void writeTo(OutputStream os) {
    Workbook book = createWorkbookWithAnalytics();
    try {
      book.write(os);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
