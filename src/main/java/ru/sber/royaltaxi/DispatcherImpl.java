package ru.sber.royaltaxi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DispatcherImpl implements Dispatcher {

  private final Queue<Taxi> availableTaxis;
  private final List<Order> orders;

  private final AtomicBoolean execute;

  private final int taxiCount;
  private final int orderCount;

  public DispatcherImpl(String name, int taxiCount, int orderCount) {
    this.taxiCount = taxiCount;
    this.orderCount = orderCount;
    execute = new AtomicBoolean(true);
    availableTaxis = new ArrayDeque<>(taxiCount);
    orders = new ArrayList<>(orderCount);
    initOrders(orderCount);
    initTaxis(taxiCount);
  }

  private void initTaxis(int taxiCount) {
    for (int i = 0; i < taxiCount; ++i) {
      TaxiImpl taxi = new TaxiImpl("Taxi " + i, this, execute);
      taxi.start();
      availableTaxis.add(taxi);
    }
  }

  private void initOrders(int orderCount) {
    for (int i = 0; i < orderCount; ++i) {
      orders.add(new Order("Order " + i, OrderType.NORMAL));
    }
  }

  @Override
  public void notifyAvailable(Taxi taxi) {
    synchronized (availableTaxis) {
      availableTaxis.add(taxi);
      availableTaxis.notify();
    }
  }

  @Override
  public void placeOrder(Taxi taxi, Order order) {
    taxi.placeOrder(order);
  }

  @Override
  public void run() {
    for (Order order : orders) {
      synchronized (availableTaxis) {
        while (availableTaxis.isEmpty()) {
          try {
            availableTaxis.wait();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
        Taxi freeTaxi = availableTaxis.poll();
        placeOrder(freeTaxi, order);
      }
    }
    end();
  }

  private void end() {
    execute.set(false);
    synchronized (availableTaxis) {
      while (availableTaxis.size() != this.taxiCount) {
        try {
          availableTaxis.wait();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      Queue<Taxi> toEndList = new ArrayDeque<>(availableTaxis);
      while (!toEndList.isEmpty()) {
        Taxi taxi = toEndList.poll();
        placeOrder(taxi, new Order("day ended", OrderType.END));
        try {
          ((TaxiImpl) taxi).join();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        System.out.println(((TaxiImpl) taxi).getName() + " : " + taxi.getExecutedOrders());
      }
    }
  }
}
