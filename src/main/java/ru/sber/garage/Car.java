package ru.sber.garage;

import java.util.Objects;

public class Car {
  @ReportFieldName("ID")
  private final long carId;
  @ReportFieldName("Brand")
  private final String brand;
  @ReportFieldName("Model Name")
  private final String modelName;
  @ReportFieldName("Maximum Velocity")
  private final long maxVelocity;
  @ReportFieldName("Power")
  private final long power;
  @ReportFieldName("Owner ID")
  private final long ownerId;

  public Car(long carId, String brand, String modelName, long maxVelocity, long power,
      long ownerId) {
    this.carId = carId;
    this.brand = brand.toLowerCase();
    this.modelName = modelName.toLowerCase();
    this.maxVelocity = maxVelocity;
    this.power = power;
    this.ownerId = ownerId;
  }

  public long getCarId() {
    return carId;
  }

  public String getBrand() {
    return brand;
  }

  public String getModelName() {
    return modelName;
  }

  public long getMaxVelocity() {
    return maxVelocity;
  }

  public long getPower() {
    return power;
  }

  public long getOwnerId() {
    return ownerId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Car car = (Car) o;
    return carId == car.carId &&
        Objects.equals(brand, car.brand) &&
        Objects.equals(modelName, car.modelName) &&
        maxVelocity == car.maxVelocity &&
        power == car.power &&
        ownerId == car.ownerId;
  }

  @Override
  public int hashCode() {
    return (int) carId;
  }

  @Override
  public String toString() {
    return Long.valueOf(carId).toString();
  }
}