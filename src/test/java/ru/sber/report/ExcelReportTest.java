package ru.sber.report;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sber.report.util.FieldExtractor;
import ru.sber.report.util.RowContentEquals;
import ru.sber.report.util.WorkBookContentEquals;

public class ExcelReportTest {

  private ExcelReport report = null;

  @BeforeEach
  public void loadReport() {
    report = new ExcelReport("testReport", Arrays.asList("field1", "field2"));
    Map<String, String> tmpEntityMap = new HashMap<>();
    tmpEntityMap.put("field1", "val11");
    tmpEntityMap.put("field2", "val12");
    report.addRow(tmpEntityMap);
  }

  @Test
  public void testAddRow() {
    Workbook goldWb = new XSSFWorkbook();
    Sheet goldSheet = goldWb.createSheet("testReport");
    Row goldRow = goldSheet.createRow(0);
    Cell goldCell1 = goldRow.createCell(0);
    goldCell1.setCellValue("val11");
    Cell goldCell2 = goldRow.createCell(1);
    goldCell2.setCellValue("val12");

    Sheet testSheet = (Sheet) FieldExtractor.extract(report, "mainSheet");
    Row testRow = testSheet.getRow(testSheet.getLastRowNum());

    assertTrue(RowContentEquals.check(testRow, goldRow));
  }

  @Test
  public void testWriteTo() {
    Workbook goldWb = (XSSFWorkbook) FieldExtractor.extract(report, "book");
    Workbook testWb;
    try {
      String TEST_FILES_PATH = "D:\\Mumtozbek\\JavaProjects\\SberJavaCourse" +
          "\\HW_Week4\\src\\test\\resources\\";
      FileOutputStream fos = new FileOutputStream(TEST_FILES_PATH + "tmpTestReport.xlsx");
      report.writeTo(fos);
      fos.close();
      FileInputStream fis = new FileInputStream(TEST_FILES_PATH + "tmpTestReport.xlsx");
      testWb = new XSSFWorkbook(fis);
      fis.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertTrue(WorkBookContentEquals.check(goldWb, testWb));
  }

  @Test
  public void testAsBytes() {
    Workbook goldWb = (XSSFWorkbook) FieldExtractor.extract(report, "book");
    Workbook testWb;
    try {
      byte[] reportBytes = report.asBytes();
      ByteArrayInputStream bis = new ByteArrayInputStream(reportBytes);
      testWb = new XSSFWorkbook(bis);
      bis.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(WorkBookContentEquals.check(goldWb, testWb));
  }
}
