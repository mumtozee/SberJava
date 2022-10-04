package ru.sber.garage;

import ru.sber.garage.util.CarPowerComparator;
import ru.sber.garage.util.CarVelocityComparator;

import java.util.*;

public class GarageService implements Garage {
  private final HashMap<Owner, HashSet<Car>> ownerToCarSet;
  private final HashMap<String, HashSet<Car>> brandToCarSet;
  private final HashMap<Long, Car> idToCar;
  private final HashMap<Long, Owner> idToOwner;
  private final TreeSet<Car> carsByVelocity;
  private final TreeSet<Car> carsByPower;

  public GarageService() {
    ownerToCarSet = new HashMap<Owner, HashSet<Car>>();
    brandToCarSet = new HashMap<String, HashSet<Car>>();
    idToCar = new HashMap<Long, Car>();
    idToOwner = new HashMap<Long, Owner>();
    carsByVelocity = new TreeSet<Car>(new CarVelocityComparator());
    carsByPower = new TreeSet<Car>(new CarPowerComparator());
  }

  @Override
  public Collection<Owner> allCarsUniqueOwners() {
    return ownerToCarSet.keySet();
  }

  @Override
  public Collection<Car> topThreeCarsByMaxVelocity() {
    Iterator<Car> it = carsByVelocity.iterator();
    ArrayList<Car> topThree = new ArrayList<>();
    for (int i = 0; i < 3; ++i) {
      topThree.add(it.next());
    }
    return topThree;
  }

  @Override
  public Collection<Car> allCarsOfBrand(String brand) {
    return brandToCarSet.get(brand.toLowerCase());
  }

  @Override
  public Collection<Car> carsWithPowerMoreThan(long power) {
    Car tmpCar = new Car(-1, "", "", 0, power + 1, -1);
    return carsByPower.headSet(tmpCar);
  }

  @Override
  public Collection<Car> allCarsOfOwner(Owner owner) {
    return ownerToCarSet.get(owner);
  }

  @Override
  public int meanOwnersAgeOfCarBrand(String brand) {
    HashSet<Car> carsOfBrand = brandToCarSet.get(brand.toLowerCase());
    HashSet<Owner> brandUniqueOwners = new HashSet<>();
    for (Car car : carsOfBrand) {
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
  public Car removeCar(long carId) {
    Car car = idToCar.get(carId);
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
    idToCar.remove(car.getCarId());
    carsByPower.remove(car);
    carsByVelocity.remove(car);
    return car;
  }

  @Override
  public boolean addCar(Car car, Owner owner) {
    return addCarToIds(car) &&
            addOwnerToIds(owner) &&
            addCarToTreeSets(car) &&
            addCarToBrands(car) &&
            addToOwners(car, owner);
  }

  private boolean addCarToIds(Car car) {
    idToCar.put(car.getCarId(), car);
    return idToCar.get(car.getCarId()).equals(car);
  }

  private boolean addOwnerToIds(Owner owner) {
    idToOwner.put(owner.getOwnerId(), owner);
    return idToOwner.get(owner.getOwnerId()).equals(owner);
  }

  private boolean addCarToTreeSets(Car car) {
    carsByPower.add(car);
    carsByVelocity.add(car);
    return carsByPower.contains(car) && carsByVelocity.contains(car);
  }

  private boolean addCarToBrands(Car car) {
    if (!brandToCarSet.containsKey(car.getBrand())) {
      brandToCarSet.put(car.getBrand(), new HashSet<Car>());
    }
    brandToCarSet.get(car.getBrand()).add(car);
    return brandToCarSet.containsKey(car.getBrand());
  }

  private boolean addToOwners(Car car, Owner owner) {
    if (!ownerToCarSet.containsKey(owner)) {
      ownerToCarSet.put(owner, new HashSet<Car>());
    }
    ownerToCarSet.get(owner).add(car);
    return ownerToCarSet.containsKey(owner);
  }
}
