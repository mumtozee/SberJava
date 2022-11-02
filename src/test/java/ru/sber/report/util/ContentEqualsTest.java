package ru.sber.report.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ContentEqualsTest {

  private static Workbook bookA = null;
  private static Workbook bookB = null;

  @BeforeAll
  public static void loadWorkbooks() {
    String fileName =
        "D:\\Mumtozbek\\JavaProjects\\SberJavaCourse" +
            "\\HW_Week4\\src\\test\\resources\\forTestContent.xlsx";
    try {
      FileInputStream fisA = new FileInputStream(fileName);
      FileInputStream fisB = new FileInputStream(fileName);
      bookA = new XSSFWorkbook(fisA);
      bookB = new XSSFWorkbook(fisB);
      fisA.close();
      fisB.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testRowContents() {
    Row rowA = bookA.sheetIterator().next().rowIterator().next();
    Row rowB = bookB.sheetIterator().next().rowIterator().next();
    assertTrue(RowContentEquals.check(rowA, rowB));
  }

  @Test
  public void testSheetContents() {
    Sheet sheetA = bookA.sheetIterator().next();
    Sheet sheetB = bookB.sheetIterator().next();
    assertTrue(SheetContentEquals.check(sheetA, sheetB));
  }

  @Test
  public void testWorkbookContents() {
    assertTrue(WorkBookContentEquals.check(bookA, bookB));
  }
}
