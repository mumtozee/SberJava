package ru.sber.garage.util;

import ru.sber.garage.SportCar;
import ru.sber.garage.Truck;

public class SportCarToTruckUpgrader implements VehicleUpgrader<SportCar, Truck> {

  @Override
  public Truck upgrade(SportCar car) {
    return new Truck(
        car.getId(),
        car.getBrand(),
        car.getModelName(),
        car.getMaxVelocity(),
        car.getPower(),
        car.getOwnerId(),
        (short) 4
    );
  }
}
