package ru.sber.garage;

import java.util.Objects;

public abstract class AbstractVehicle implements Vehicle {

  private final long id;
  private final String brand;
  private final String modelName;
  private final long maxVelocity;
  private final long power;
  private final long ownerId;

  public AbstractVehicle(
      long id,
      String brand,
      String modelName,
      long maxVelocity,
      long power,
      long ownerId) {
    this.id = id;
    this.brand = brand.toLowerCase();
    this.modelName = modelName.toLowerCase();
    this.maxVelocity = maxVelocity;
    this.power = power;
    this.ownerId = ownerId;
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public String getBrand() {
    return brand;
  }

  @Override
  public String getModelName() {
    return modelName;
  }

  @Override
  public long getMaxVelocity() {
    return maxVelocity;
  }

  @Override
  public long getPower() {
    return power;
  }

  @Override
  public long getOwnerId() {
    return ownerId;
  }

  @Override
  public int hashCode() {
    return (int) getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractVehicle that)) {
      return false;
    }
    return getId() == that.getId() &&
        getMaxVelocity() == that.getMaxVelocity() &&
        getPower() == that.getPower() &&
        getOwnerId() == that.getOwnerId() &&
        getBrand().equals(that.getBrand()) &&
        getModelName().equals(that.getModelName());
  }
}
