package ru.sber.report;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sber.garage.Car;
import ru.sber.garage.Garage;
import ru.sber.garage.GarageService;
import ru.sber.garage.Owner;
import ru.sber.report.util.WorkBookContentEquals;

public class AnalyticsTest {

  private ExcelReportAnalyticsDecorator reportAnalytics;

  @BeforeEach
  void loadReportAnalytics() {
    Garage garage = new GarageService();
    ExcelReportGenerator<Car> gen = new ExcelReportGenerator<>();
    Owner owner = new Owner(1, "", "", 0);
    List<Car> cars = Arrays.asList(
        new Car(1, "BMW", "X5", 230, 12, 1),
        new Car(2, "Mercedes", "Benz", 250, 20, 1),
        new Car(3, "Lada", "Granta", 200, 10, 1));
    for (Car car : cars) {
      garage.addCar(car, owner);
    }
    reportAnalytics = new ExcelReportAnalyticsDecorator(
        gen.generate(cars)
    );
    reportAnalytics.addAnalytics(
        "All cars with power more than 9",
        garage.carsWithPowerMoreThan(9)
    );
    reportAnalytics.addAnalytics("All ferrari cars", garage.allCarsOfBrand("Ferrari"));
  }

  @Test
  public void testAddAnalytics() {
    Workbook goldWb;
    Workbook testWb;
    try {
      String TEST_FILES_PATH = "D:\\Mumtozbek\\JavaProjects\\SberJavaCourse" +
          "\\HW_Week4\\src\\test\\resources\\";
      FileInputStream fis = new FileInputStream(TEST_FILES_PATH + "sampleReportWithAnalytics.xlsx");
      goldWb = new XSSFWorkbook(fis);
      fis.close();

      byte[] reportWithAnalyticsBytes = reportAnalytics.asBytes();
      ByteArrayInputStream bis = new ByteArrayInputStream(reportWithAnalyticsBytes);
      testWb = new XSSFWorkbook(bis);
      bis.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(WorkBookContentEquals.check(testWb, goldWb));
  }
}
