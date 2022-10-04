package ru.sber.garage;

import org.junit.jupiter.api.Test;
import ru.sber.garage.util.CarPowerComparator;
import ru.sber.garage.util.CarVelocityComparator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GarageServiceTest {
  @Test
  public void testAddCar() {
    GarageService garage = new GarageService();
    Owner owner = new Owner(1, "", "", 12);
    Car car = new Car(1, "", "", 0, 0, 1);
    boolean result = garage.addCar(car, owner);
    assertTrue(result);
  }

  @Test
  public void testRemoveCar() {
    GarageService garage = new GarageService();
    Owner owner = new Owner(1, "", "", 12);
    Car car = new Car(1, "", "", 0, 0, 1);
    garage.addCar(car, owner);
    Car removedCar = garage.removeCar(car.getCarId());
    assertEquals(car, removedCar);
  }

  @Test
  public void testAllCarsUniqueOwners() {
    GarageService garage = new GarageService();
    List<Owner> ownerList = Arrays.asList(
            new Owner(1, "John", "Doe", 12),
            new Owner(2, "Sara", "Conor", 25)
    );
    long cardId = 1;
    for (Owner owner : ownerList) {
      garage.addCar(new Car(cardId, "Lada", "Granta", 230, 10, owner.getOwnerId()), owner);
      garage.addCar(new Car(cardId + 1, "BMW", "X5", 430, 100, owner.getOwnerId()), owner);
      cardId += 2;
    }
    HashSet<Owner> uniqueOwners = new HashSet<Owner>(garage.allCarsUniqueOwners());
    assertEquals(uniqueOwners, new HashSet<Owner>(ownerList));
  }

  @Test
  public void testTopThreeCarsByMaxVelocity() {
    GarageService garage = new GarageService();
    Owner owner = new Owner(1, "", "", 10);
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1),
            new Car(2, "BMW", "", 250, 120, 1),
            new Car(3, "Mercedes", "", 250, 120, 1),
            new Car(4, "Lada", "", 230, 120, 1),
            new Car(5, "Ferrari", "", 230, 120, 1)
    );
    for (Car car : carList) {
      garage.addCar(car, owner);
    }
    TreeSet<Car> topThree = new TreeSet<>(new CarVelocityComparator());
    topThree.addAll(garage.topThreeCarsByMaxVelocity());
    TreeSet<Car> goldTopThree = new TreeSet<>(new CarVelocityComparator());
    goldTopThree.add(carList.get(1));
    goldTopThree.add(carList.get(2));
    goldTopThree.add(carList.get(4));
    assertEquals(topThree, goldTopThree);
  }

  @Test
  public void testAllCarsOfBrand() {
    GarageService garage = new GarageService();
    Owner owner = new Owner(1, "", "", 10);
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1),
            new Car(2, "Ferrari", "", 250, 120, 1),
            new Car(3, "BMW", "", 250, 120, 1)
    );
    for (Car car : carList) {
      garage.addCar(car, owner);
    }
    HashSet<Car> allFerrari = new HashSet<>(garage.allCarsOfBrand("ferrari"));
    HashSet<Car> goldAllFerrari = new HashSet<>();
    goldAllFerrari.add(carList.get(0));
    goldAllFerrari.add(carList.get(1));
    assertEquals(allFerrari, goldAllFerrari);
  }

  @Test
  public void testCarsWithPowerMoreThan() {
    GarageService garage = new GarageService();
    Owner owner = new Owner(1, "", "", 10);
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1),
            new Car(2, "Ferrari", "", 250, 100, 1),
            new Car(3, "BMW", "", 250, 80, 1)
    );
    for (Car car : carList) {
      garage.addCar(car, owner);
    }
    TreeSet<Car> carsWithPower = new TreeSet<>(new CarPowerComparator());
    carsWithPower.addAll(garage.carsWithPowerMoreThan(80));
    TreeSet<Car> goldCarsWithPower = new TreeSet<>(new CarPowerComparator());
    goldCarsWithPower.add(carList.get(0));
    goldCarsWithPower.add(carList.get(1));
    assertEquals(carsWithPower, goldCarsWithPower);
  }

  @Test
  public void testAllCarsOfOwner() {
    GarageService garage = new GarageService();
    List<Owner> ownerList = Arrays.asList(
            new Owner(1, "John", "Doe", 12),
            new Owner(2, "Sara", "Conor", 25)
    );
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1),
            new Car(2, "Ferrari", "", 250, 100, 2),
            new Car(3, "BMW", "", 250, 80, 1)
    );
    for (Car car : carList) {
      garage.addCar(car, ownerList.get((int) (car.getOwnerId() - 1)));
    }
    HashSet<Car> ownerCars = new HashSet<>(garage.allCarsOfOwner(ownerList.get(0)));
    HashSet<Car> goldOwnerCars = new HashSet<>();
    goldOwnerCars.add(carList.get(0));
    goldOwnerCars.add(carList.get(2));
    assertEquals(ownerCars, goldOwnerCars);
  }

  @Test
  public void testMeanOwnersAgeOfCarBrand() {
    GarageService garage = new GarageService();
    List<Owner> ownerList = Arrays.asList(
            new Owner(1, "John", "Doe", 12),
            new Owner(2, "Sara", "Conor", 25),
            new Owner(3, "Kim", "Tan", 35)
    );
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1),
            new Car(2, "Ferrari", "", 250, 100, 2),
            new Car(3, "BMW", "", 250, 80, 1),
            new Car(4, "BMW", "", 250, 80, 2),
            new Car(5, "BMW", "", 254, 890, 1),
            new Car(5, "BMW", "", 254, 890, 3)
    );
    for (Car car : carList) {
      garage.addCar(car, ownerList.get((int) (car.getOwnerId() - 1)));
    }
    int meanAge = garage.meanOwnersAgeOfCarBrand("Bmw");
    int goldMeanAge = 24;
    assertEquals(goldMeanAge, meanAge);
  }

  @Test
  public void testMeanCarNumberForEachOwner() {
    GarageService garage = new GarageService();
    List<Owner> ownerList = Arrays.asList(
            new Owner(1, "John", "Doe", 12),
            new Owner(2, "Sara", "Conor", 25),
            new Owner(3, "Kim", "Tan", 35)
    );
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1),
            new Car(2, "Ferrari", "", 250, 100, 2),
            new Car(3, "BMW", "", 250, 80, 1),
            new Car(4, "BMW", "", 250, 80, 2),
            new Car(5, "BMW", "", 254, 890, 1),
            new Car(5, "BMW", "", 254, 890, 3)
    );
    for (Car car : carList) {
      garage.addCar(car, ownerList.get((int) (car.getOwnerId() - 1)));
    }
    long meanCarNumber = garage.meanCarNumberForEachOwner();
    long goldMeanCarNumber = 2;
    assertEquals(goldMeanCarNumber, meanCarNumber);
  }
}
