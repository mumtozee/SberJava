package ru.sber.garage.util;

import java.util.Comparator;

import ru.sber.garage.Car;

public class CarVelocityComparator implements Comparator<Car> {
  @Override
  public int compare(Car carA, Car carB) {
    int velocityCompare = Long.compare(carA.getMaxVelocity(), carB.getMaxVelocity());
    if (velocityCompare == 0) {
      return (-1) * Long.compare(carA.getCarId(), carB.getCarId());
    }
    return (-1) * velocityCompare;
  }
}
