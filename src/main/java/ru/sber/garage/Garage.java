package ru.sber.garage;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import ru.sber.garage.util.VehicleUpgrader;

public interface Garage<T extends Vehicle> {

  Collection<Owner> allCarsUniqueOwners();

  /**
   * Complexity should be less than O(n)
   */
  Collection<T> topThreeCarsByMaxVelocity();

  /**
   * Complexity should be O(1)
   */
  Collection<T> allCarsOfBrand(String brand);

  /**
   * Complexity should be less than O(n)
   */
  Collection<T> carsWithPowerMoreThan(long power);

  /**
   * Complexity should be O(1)
   */
  Collection<T> allCarsOfOwner(Owner owner);

  /**
   * @return mean value of owner age that has cars with given brand
   */
  int meanOwnersAgeOfCarBrand(String brand);

  /**
   * @return mean value of cars for all owners
   */
  long meanCarNumberForEachOwner();

  /**
   * Complexity should be less than O(n)
   *
   * @return removed car
   */
  T removeCar(long carId);

  /**
   * Complexity should be less than O(n)
   */
  boolean addCar(T car, Owner owner);

  boolean addCars(List<T> cars, List<Owner> owners);

  T removeCar(T car);

  List<T> removeAll(List<T> cars);

  <NewT extends Vehicle> List<NewT> upgradeAllVehiclesWith(
      VehicleUpgrader<T, NewT> upgrader);

  List<T> filterCars(Predicate<T> predicate);
}
