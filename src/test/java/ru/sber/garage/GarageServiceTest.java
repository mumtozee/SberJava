package ru.sber.garage;

import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import ru.sber.garage.util.PowerComparator;
import ru.sber.garage.util.SportCarToTruckUpgrader;
import ru.sber.garage.util.VelocityComparator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GarageServiceTest {
  @Test
  public void testAddCar() {
    GarageImpl<Car> garage = new GarageImpl<>();
    Owner owner = new Owner(1, "", "", 12);
    Car car = new Car(1, "", "", 0, 0, 1, (short) 4);
    boolean result = garage.addCar(car, owner);
    assertTrue(result);
  }
  @Test
  public void testAddCars() {
    GarageImpl<Truck> garage = new GarageImpl<>();
    List<Owner> owners = new ArrayList<>();
    owners.add(new Owner(1, "", "", 12));
    owners.add(new Owner(2, "", "", 12));
    owners.add(new Owner(2, "", "", 12));

    List<Truck> cars = new ArrayList<>();
    cars.add(new Truck(1, "", "", 0, 0, 1, (short) 4));
    cars.add(new Truck(2, "", "", 0, 0, 2, (short) 6));
    cars.add(new Truck(3, "", "", 0, 0, 2, (short) 6));
    boolean result = garage.addCars(cars, owners);
    assertTrue(result);
  }

  @Test
  public void testRemoveCar() {
    GarageImpl<Car> garage = new GarageImpl<>();
    Owner owner = new Owner(1, "", "", 12);
    Car car = new Car(1, "", "", 0, 0, 1, (short) 4);
    garage.addCar(car, owner);
    Car removedCar = garage.removeCar(car.getId());
    assertEquals(car, removedCar);
  }

  @Test
  public void testRemoveAll() {
    GarageImpl<Car> garage = new GarageImpl<>();
    Owner owner = new Owner(1, "", "", 10);
    List<Car> carList = Arrays.asList(
        new Car(1, "Ferrari", "", 230, 120, 1, (short) 4),
        new Car(2, "BMW", "", 250, 120, 1, (short) 4),
        new Car(3, "Mercedes", "", 250, 120, 1, (short) 4),
        new Car(4, "Lada", "", 230, 120, 1, (short) 4),
        new Car(5, "Ferrari", "", 230, 120, 1, (short) 4)
    );
    for (Car car : carList) {
      garage.addCar(car, owner);
    }
    List<Car> removed = (List<Car>) garage.removeAll(carList);
    assertNull(garage.allCarsOfOwner(owner));
  }

  @Test
  public void testFilterCars() {
    GarageImpl<SportCar> garage = new GarageImpl<>();
    Predicate<SportCar> engineFilter = car -> car.getEngineVolume() < 1.2f;
    Owner owner = new Owner(1, "", "", 10);
    List<SportCar> carList = Arrays.asList(
        new SportCar(1, "Ferrari", "", 230, 120, 1, 2f),
        new SportCar(2, "BMW", "", 250, 120, 1, 3f),
        new SportCar(3, "Mercedes", "", 250, 120, 1, 1.2f),
        new SportCar(4, "Lada", "", 230, 120, 1, 0.7f),
        new SportCar(5, "Ferrari", "", 230, 120, 1, 0.5f)
    );
    for (SportCar car : carList) {
      garage.addCar(car, owner);
    }
    List<SportCar> filtered = (List<SportCar>) garage.filterCars(engineFilter);
    boolean result = true;
    for (SportCar car : filtered) {
      result &= car.getEngineVolume() < 1.2f;
    }
    assertTrue(result);
  }

  @Test
  public void testAllCarsUniqueOwners() {
    GarageImpl<Car> garage = new GarageImpl<>();
    List<Owner> ownerList = Arrays.asList(
            new Owner(1, "John", "Doe", 12),
            new Owner(2, "Sara", "Conor", 25)
    );
    long cardId = 1;
    for (Owner owner : ownerList) {
      garage.addCar(new Car(cardId, "Lada", "Granta", 230, 10, owner.getOwnerId(), (short) 4), owner);
      garage.addCar(new Car(cardId + 1, "BMW", "X5", 430, 100, owner.getOwnerId(), (short) 4), owner);
      cardId += 2;
    }
    HashSet<Owner> uniqueOwners = new HashSet<Owner>(garage.allCarsUniqueOwners());
    assertEquals(uniqueOwners, new HashSet<Owner>(ownerList));
  }

  @Test
  public void testTopThreeCarsByMaxVelocity() {
    GarageImpl<Car> garage = new GarageImpl<>();
    Owner owner = new Owner(1, "", "", 10);
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1, (short) 4),
            new Car(2, "BMW", "", 250, 120, 1, (short) 4),
            new Car(3, "Mercedes", "", 250, 120, 1, (short) 4),
            new Car(4, "Lada", "", 230, 120, 1, (short) 4),
            new Car(5, "Ferrari", "", 230, 120, 1, (short) 4)
    );
    for (Car car : carList) {
      garage.addCar(car, owner);
    }
    TreeSet<Car> topThree = new TreeSet<>(new VelocityComparator<>());
    topThree.addAll(garage.topThreeCarsByMaxVelocity());
    TreeSet<Car> goldTopThree = new TreeSet<>(new VelocityComparator<>());
    goldTopThree.add(carList.get(1));
    goldTopThree.add(carList.get(2));
    goldTopThree.add(carList.get(4));
    assertEquals(topThree, goldTopThree);
  }

  @Test
  public void testAllCarsOfBrand() {
    GarageImpl<Car> garage = new GarageImpl<>();
    Owner owner = new Owner(1, "", "", 10);
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1, (short) 4),
            new Car(2, "Ferrari", "", 250, 120, 1, (short) 4),
            new Car(3, "BMW", "", 250, 120, 1, (short) 4)
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
    GarageImpl<Car> garage = new GarageImpl<>();
    Owner owner = new Owner(1, "", "", 10);
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1, (short) 4),
            new Car(2, "Ferrari", "", 250, 100, 1, (short) 4),
            new Car(3, "BMW", "", 250, 80, 1, (short) 4)
    );
    for (Car car : carList) {
      garage.addCar(car, owner);
    }
    TreeSet<Car> carsWithPower = new TreeSet<>(new PowerComparator<>());
    carsWithPower.addAll(garage.carsWithPowerMoreThan(80));
    TreeSet<Car> goldCarsWithPower = new TreeSet<>(new PowerComparator<>());
    goldCarsWithPower.add(carList.get(0));
    goldCarsWithPower.add(carList.get(1));
    assertEquals(carsWithPower, goldCarsWithPower);
  }

  @Test
  public void testAllCarsOfOwner() {
    GarageImpl<Car> garage = new GarageImpl<>();
    List<Owner> ownerList = Arrays.asList(
            new Owner(1, "John", "Doe", 12),
            new Owner(2, "Sara", "Conor", 25)
    );
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1, (short) 4),
            new Car(2, "Ferrari", "", 250, 100, 2, (short) 4),
            new Car(3, "BMW", "", 250, 80, 1, (short) 4)
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
    GarageImpl<Car> garage = new GarageImpl<>();
    List<Owner> ownerList = Arrays.asList(
            new Owner(1, "John", "Doe", 12),
            new Owner(2, "Sara", "Conor", 25),
            new Owner(3, "Kim", "Tan", 35)
    );
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1, (short) 4),
            new Car(2, "Ferrari", "", 250, 100, 2, (short) 4),
            new Car(3, "BMW", "", 250, 80, 1, (short) 4),
            new Car(4, "BMW", "", 250, 80, 2, (short) 4),
            new Car(5, "BMW", "", 254, 890, 1, (short) 4),
            new Car(5, "BMW", "", 254, 890, 3, (short) 4)
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
    GarageImpl<Car> garage = new GarageImpl<>();
    List<Owner> ownerList = Arrays.asList(
            new Owner(1, "John", "Doe", 12),
            new Owner(2, "Sara", "Conor", 25),
            new Owner(3, "Kim", "Tan", 35)
    );
    List<Car> carList = Arrays.asList(
            new Car(1, "Ferrari", "", 230, 120, 1, (short) 4),
            new Car(2, "Ferrari", "", 250, 100, 2, (short) 4),
            new Car(3, "BMW", "", 250, 80, 1, (short) 4),
            new Car(4, "BMW", "", 250, 80, 2, (short) 4),
            new Car(5, "BMW", "", 254, 890, 1, (short) 4),
            new Car(5, "BMW", "", 254, 890, 3, (short) 4)
    );
    for (Car car : carList) {
      garage.addCar(car, ownerList.get((int) (car.getOwnerId() - 1)));
    }
    long meanCarNumber = garage.meanCarNumberForEachOwner();
    long goldMeanCarNumber = 2;
    assertEquals(goldMeanCarNumber, meanCarNumber);
  }

  @Test
  public void testUpgradeAllVehiclesWith() {
    Garage<SportCar> garage = new GarageImpl<>();
    Owner owner = new Owner(1, "", "", 10);
    List<SportCar> carList = Arrays.asList(
        new SportCar(1, "Ferrari", "", 230, 120, 1, 3.f),
        new SportCar(2, "Ferrari", "", 250, 100, 1, 3.f),
        new SportCar(3, "BMW", "", 250, 80, 1, 2.f)
    );
    for (SportCar car : carList) {
      garage.addCar(car, owner);
    }
    List<Truck> truckList = Arrays.asList(
        new Truck(1, "Ferrari", "", 230, 120, 1, (short) 4),
        new Truck(2, "Ferrari", "", 250, 100, 1, (short) 4),
        new Truck(3, "BMW", "", 250, 80, 1, (short) 4)
    );
    List<? super Vehicle> converted = garage.upgradeAllVehiclesWith(new SportCarToTruckUpgrader());
    assertEquals(converted, truckList);
  }
}
