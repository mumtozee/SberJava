package ru.sber.garage;

public class SportCar extends AbstractVehicle {

  private final float engineVolume;

  public SportCar(
      long id,
      String brand,
      String modelName,
      long maxVelocity,
      long power,
      long ownerId,
      float engineVolume) {
    super(id, brand, modelName, maxVelocity, power, ownerId);
    this.engineVolume = engineVolume;
  }

  public float getEngineVolume() {
    return engineVolume;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SportCar car)) {
      return false;
    }
    return super.equals(o) && getEngineVolume() == car.getEngineVolume();
  }
}
