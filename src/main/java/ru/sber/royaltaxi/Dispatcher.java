package ru.sber.royaltaxi;

public interface Dispatcher {
  void notifyAvailable(Taxi taxi);

  void placeOrder(Taxi taxi, Order order);

  void run();
}
