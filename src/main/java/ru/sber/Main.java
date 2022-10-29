package ru.sber;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ru.sber.garage.Car;
import ru.sber.garage.Garage;
import ru.sber.garage.GarageService;
import ru.sber.garage.Owner;
import ru.sber.report.ExcelReportAnalyticsDecorator;
import ru.sber.report.ExcelReportGenerator;

public class Main {

  public static void main(String[] args) throws IOException {
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
    ExcelReportAnalyticsDecorator reportAnalytics = new ExcelReportAnalyticsDecorator(gen.generate(cars));
    reportAnalytics.addAnalytics("All cars with power more than 9", garage.carsWithPowerMoreThan(9));
    reportAnalytics.addAnalytics("All ferrari cars", garage.allCarsOfBrand("Ferrari"));
    String filename = "D:/Mumtozbek/JavaProjects/SberJavaCourse/HW_Week4/cars.xlsx";
    try (FileOutputStream fos = new FileOutputStream(filename)) {
      reportAnalytics.writeTo(fos);
    }
  }
}