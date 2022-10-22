package ru.sber;

import ru.sber.garage.GarageImpl;
import ru.sber.garage.Owner;
import ru.sber.garage.Car;

public class Main {

  public static void main(String[] args) {
    Owner chel1 = new Owner(1, "John", "Doe", 20);
    Owner chel2 = new Owner(2, "James", "Hobs", 22);
    Owner chel3 = new Owner(3, "Rick", "Morty", 30);
//    Owner chel4 = new Owner(4, "Sara", "Conor", 32);
//    Owner chel5 = new Owner(5, "Masha", "Marina", 34);
//    Owner chel6 = new Owner(6, "Ivan", "Ivanov", 45);

    Car car1 = new Car(1, "BMW", "", 320, 13, 1, (short) 4);
    Car car2 = new Car(2, "Mercedes", "", 230, 73, 1, (short) 4);
    Car car3 = new Car(3, "BMW", "", 460, 22, 2, (short) 4);
    Car car4 = new Car(4, "Ferrari", "", 460, 20, 2, (short) 4);
    Car car5 = new Car(5, "Mercedes", "", 460, 63, 3, (short) 4);
    Car car6 = new Car(6, "Lada", "Granta", 300, 33, 3, (short) 4);
    GarageImpl<Car> service = new GarageImpl<>();
    service.addCar(car1, chel1);
    service.addCar(car2, chel1);
    service.addCar(car3, chel2);
    service.addCar(car4, chel2);
    service.addCar(car5, chel3);
    service.addCar(car6, chel3);
//    for (Car c : service.carsWithPowerMoreThan(33)) {
//      System.out.println(c.getPower());
//    }
    double a = 2.5;
    System.out.println((int) Math.round(a));
  }
}