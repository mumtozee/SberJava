package ru.sber.garage;

import java.util.Objects;

public class Owner {
  private final long ownerId;
  private final String name;
  private final String lastName;
  private final int age;

  public Owner(long ownerId, String name, String lastName, int age) {
    this.ownerId = ownerId;
    this.name = name;
    this.lastName = lastName;
    this.age = age;
  }

  public long getOwnerId() {
    return ownerId;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  public int getAge() {
    return age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Owner owner = (Owner) o;
    return ownerId == owner.ownerId &&
            Objects.equals(name, owner.name) &&
            Objects.equals(lastName, owner.lastName) &&
            age == owner.age;
  }

  @Override
  public int hashCode() {
    return (int) ownerId;
  }
}
