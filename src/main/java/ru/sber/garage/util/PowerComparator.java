package ru.sber.garage.util;

import ru.sber.garage.Vehicle;

import java.util.Comparator;

public class PowerComparator<T extends Vehicle> implements Comparator<T> {
  @Override
  public int compare(T vehicleA, T vehicleB) {
    int powerCompare = Long.compare(vehicleA.getPower(), vehicleB.getPower());
    if (powerCompare == 0) {
      return (-1) * Long.compare(vehicleA.getId(), vehicleB.getId());
    }
    return (-1) * powerCompare;
  }
}
