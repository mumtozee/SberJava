package ru.sber.garage;

public class Truck extends AbstractVehicle {

  private final short numOfWheels;

  public Truck(
      long id,
      String brand,
      String modelName,
      long maxVelocity,
      long power,
      long ownerId,
      short numOfWheels) {
    super(id, brand, modelName, maxVelocity, power, ownerId);
    this.numOfWheels = numOfWheels;
  }

  public short getNumOfWheels() {
    return numOfWheels;
  }
}
