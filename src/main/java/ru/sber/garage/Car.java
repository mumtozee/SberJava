package ru.sber.garage;

import java.util.Objects;

public class Car extends AbstractVehicle {

  private final short numOfSeats;

  public Car(
      long carId,
      String brand,
      String modelName,
      long maxVelocity,
      long power,
      long ownerId,
      short numOfSeats) {
    super(carId, brand, modelName, maxVelocity, power, ownerId);
    this.numOfSeats = numOfSeats;
  }

  public short getNumOfSeats() {
    return numOfSeats;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Car car)) {
      return false;
    }
    return super.equals(o) && getNumOfSeats() == car.getNumOfSeats();
  }
}
