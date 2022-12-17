package ru.sber.garage;

import java.util.function.Predicate;
import ru.sber.garage.util.PowerComparator;
import ru.sber.garage.util.VehicleUpgrader;
import ru.sber.garage.util.VelocityComparator;

import java.util.*;

public class GarageImpl<T extends Vehicle> implements Garage<T> {

  private final Map<Owner, HashSet<T>> ownerToCarSet;
  private final Map<String, HashSet<T>> brandToCarSet;
  private final Map<Long, T> idToCar;
  private final Map<Long, Owner> idToOwner;
  private final TreeSet<T> carsByVelocity;
  private final TreeSet<T> carsByPower;

  public GarageImpl() {
    ownerToCarSet = new HashMap<Owner, HashSet<T>>();
    brandToCarSet = new HashMap<String, HashSet<T>>();
    idToCar = new HashMap<Long, T>();
    idToOwner = new HashMap<Long, Owner>();
    carsByVelocity = new TreeSet<T>(new VelocityComparator<T>());
    carsByPower = new TreeSet<T>(new PowerComparator<T>());
  }

  @Override
  public Collection<Owner> allCarsUniqueOwners() {
    return ownerToCarSet.keySet();
  }

  @Override
  public Collection<T> topThreeCarsByMaxVelocity() {
    Iterator<T> it = carsByVelocity.iterator();
    ArrayList<T> topThree = new ArrayList<>();
    for (int i = 0; i < 3; ++i) {
      topThree.add(it.next());
    }
    return topThree;
  }

  @Override
  public Collection<T> allCarsOfBrand(String brand) {
    return brandToCarSet.get(brand.toLowerCase());
  }

  @Override
  public Collection<T> carsWithPowerMoreThan(long power) {
    return carsByPower.headSet((T) new AbstractVehicle(-1, "", "", 0, power + 1, -1) {
    });
  }

  @Override
  public Collection<T> allCarsOfOwner(Owner owner) {
    return ownerToCarSet.get(owner);
  }

  @Override
  public int meanOwnersAgeOfCarBrand(String brand) {
    HashSet<T> carsOfBrand = brandToCarSet.get(brand.toLowerCase());
    HashSet<Owner> brandUniqueOwners = new HashSet<>();
    for (T car : carsOfBrand) {
      brandUniqueOwners.add(idToOwner.get(car.getOwnerId()));
    }

    double meanAge = 0;
    int count = 0;
    for (Owner owner : brandUniqueOwners) {
      meanAge *= count;
      meanAge += owner.getAge();
      ++count;
      meanAge /= count;
    }
    return (int) Math.round(meanAge);
  }

  @Override
  public long meanCarNumberForEachOwner() {
    long count = 0;
    double meanCarNumber = 0;
    for (Owner owner : ownerToCarSet.keySet()) {
      meanCarNumber *= count;
      meanCarNumber += ownerToCarSet.get(owner).size();
      ++count;
      meanCarNumber /= count;
    }
    return (long) Math.round(meanCarNumber);
  }

  @Override
  public T removeCar(long carId) {
    T car = idToCar.get(carId);
    if (car == null) {
      return null;
    }
    Owner owner = idToOwner.get(car.getOwnerId());
    brandToCarSet.get(car.getBrand()).remove(car);
    if (brandToCarSet.get(car.getBrand()).isEmpty()) {
      brandToCarSet.remove(car.getBrand());
    }
    ownerToCarSet.get(owner).remove(car);
    if (ownerToCarSet.get(owner).isEmpty()) {
      ownerToCarSet.remove(owner);
      idToOwner.remove(owner.getOwnerId());
    }
    idToCar.remove(car.getId());
    carsByPower.remove(car);
    carsByVelocity.remove(car);
    return car;
  }

  @Override
  public boolean addCar(T car, Owner owner) {
    return addCarToIds(car) &&
        addOwnerToIds(owner) &&
        addCarToTreeSets(car) &&
        addCarToBrands(car) &&
        addToOwners(car, owner);
  }

  @Override
  public boolean addCars(List<? extends T> cars, List<Owner> owners) {
    boolean result = true;
    for (int i = 0; i < cars.size(); i++) {
      result &= addCar((T) cars.get(i), owners.get(i));
    }
    return result;
  }

  @Override
  public T removeCar(T car) {
    return removeCar(car.getId());
  }

  @Override
  public List<T> removeAll(List<? extends T> cars) {
    List<T> removedCars = new ArrayList<>();
    for (T car : cars) {
      removedCars.add(removeCar(car));
    }
    return removedCars;
  }

  @Override
  public List<? super Vehicle> upgradeAllVehiclesWith(
      VehicleUpgrader<T, ? extends Vehicle> upgrader) {
    List<? super Vehicle> result = new ArrayList<>();
    for (T car: idToCar.values()) {
      result.add(upgrader.upgrade(car));
    }
    return result;
  }

  @Override
  public List<T> filterCars(Predicate<? super T> predicate) {
    List<T> filtered = new ArrayList<>();
    for (T car : idToCar.values()) {
      if (predicate.test(car)) {
        filtered.add(car);
      }
    }
    return filtered;
  }

  private boolean addCarToIds(T car) {
    idToCar.put(car.getId(), car);
    return idToCar.get(car.getId()).equals(car);
  }

  private boolean addOwnerToIds(Owner owner) {
    idToOwner.put(owner.getOwnerId(), owner);
    return idToOwner.get(owner.getOwnerId()).equals(owner);
  }

  private boolean addCarToTreeSets(T car) {
    carsByPower.add(car);
    carsByVelocity.add(car);
    return carsByPower.contains(car) && carsByVelocity.contains(car);
  }

  private boolean addCarToBrands(T car) {
    if (!brandToCarSet.containsKey(car.getBrand())) {
      brandToCarSet.put(car.getBrand(), new HashSet<T>());
    }
    brandToCarSet.get(car.getBrand()).add(car);
    return brandToCarSet.containsKey(car.getBrand());
  }

  private boolean addToOwners(T car, Owner owner) {
    if (!ownerToCarSet.containsKey(owner)) {
      ownerToCarSet.put(owner, new HashSet<T>());
    }
    ownerToCarSet.get(owner).add(car);
    return ownerToCarSet.containsKey(owner);
  }
}
