package ru.sber.royaltaxi;

import java.util.List;

public interface Taxi {
  void run();

  void placeOrder(Order order);

  List<Order> getExecutedOrders();
}
