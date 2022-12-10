package ru.sber.royaltaxi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaxiImpl extends Thread implements Taxi {
  private final OrderWrapper currentOrder;

  private final Dispatcher dispatcher;
  private final List<Order> executedList;

  private final AtomicBoolean execute;

  public TaxiImpl(String name, Dispatcher dispatcher, AtomicBoolean execute) {
    super(name);
    currentOrder = new OrderWrapper();
    executedList = new ArrayList<>();
    this.dispatcher = dispatcher;
    this.execute = execute;
  }

  @Override
  public void run() {
    while (execute.get()) {
      synchronized (currentOrder) {
        while (currentOrder.isNull()) {
          try {
            currentOrder.wait();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
        if (currentOrder.getOrder().getType() != OrderType.END) {
          try {
            synchronized (executedList) {
              currentOrder.getOrder().execute();
              executedList.add(currentOrder.getOrder());
              System.out.println(this.getName() + " executed the " + currentOrder.getOrder().getName());
              currentOrder.setOrder(null);
              dispatcher.notifyAvailable(this);
            }
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
  }

  @Override
  public void placeOrder(Order order) {
    synchronized (currentOrder) {
      currentOrder.setOrder(order);
      currentOrder.notify();
    }
  }

  @Override
  public List<Order> getExecutedOrders() {
    synchronized (executedList) {
      return executedList;
    }
  }
}
