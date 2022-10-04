package ru.sber.garage.util;

import ru.sber.garage.Car;

import java.util.Comparator;

public class CarPowerComparator implements Comparator<Car> {
  @Override
  public int compare(Car carA, Car carB) {
    int powerCompare = Long.compare(carA.getPower(), carB.getPower());
    if (powerCompare == 0) {
      return (-1) * Long.compare(carA.getCarId(), carB.getCarId());
    }
    return (-1) * powerCompare;
  }
}
