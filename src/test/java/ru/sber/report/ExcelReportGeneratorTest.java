package ru.sber.report;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import ru.sber.garage.ReportFieldName;
import ru.sber.report.util.FieldExtractor;
import ru.sber.report.util.WorkBookContentEquals;

class DullObject {

  @ReportFieldName("field1")
  public String dullAttributeA;
  public String field2;

  DullObject(String val1, String val2) {
    dullAttributeA = val1;
    field2 = val2;
  }
}

public class ExcelReportGeneratorTest {

  @Test
  public void testGenerate() {
    List<DullObject> entities = Arrays.asList(
        new DullObject("val11", "val12"),
        new DullObject("val21", "val22"),
        new DullObject("val31", "val32")
    );
    ExcelReportGenerator<DullObject> generator = new ExcelReportGenerator<>();
    ExcelReport report = generator.generate(entities);
    Workbook goldWb;

    try {
      String TEST_FILES_PATH = "D:\\Mumtozbek\\JavaProjects\\SberJavaCourse" +
          "\\HW_Week4\\src\\test\\resources\\";
      FileInputStream fis = new FileInputStream(TEST_FILES_PATH + "sampleReport.xlsx");
      goldWb = new XSSFWorkbook(fis);
      fis.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Workbook testWb = (XSSFWorkbook) FieldExtractor.extract(report, "book");
    assertTrue(WorkBookContentEquals.check(testWb, goldWb));
  }
}
