package ru.sber.garage.util;

import java.util.Comparator;

import ru.sber.garage.AbstractVehicle;
import ru.sber.garage.Vehicle;

public class VelocityComparator<T extends Vehicle> implements Comparator<T> {
  @Override
  public int compare(T vehicleA, T vehicleB) {
    int velocityCompare = Long.compare(vehicleA.getMaxVelocity(), vehicleB.getMaxVelocity());
    if (velocityCompare == 0) {
      return (-1) * Long.compare(vehicleA.getId(), vehicleB.getId());
    }
    return (-1) * velocityCompare;
  }
}
