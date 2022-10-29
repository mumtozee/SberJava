package ru.sber.garage;

import java.util.Objects;

public class Owner {
  @ReportFieldName("ID")
  private final long ownerId;
  @ReportFieldName("Name")
  private final String name;
  @ReportFieldName("Last Name")
  private final String lastName;
  @ReportFieldName("Age")
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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
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

  @Override
  public String toString() {
    return Long.valueOf(ownerId).toString();
  }
}