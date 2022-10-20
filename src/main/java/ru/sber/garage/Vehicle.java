package ru.sber.garage;

public interface Vehicle {
  long getId();
  long getOwnerId();
  long getMaxVelocity();
  long getPower();
  String getBrand();
  String getModelName();
}
