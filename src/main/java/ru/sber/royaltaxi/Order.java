package ru.sber.royaltaxi;

import java.util.concurrent.ThreadLocalRandom;

public class Order {
  private final int timeToSleep;

  private final String name;
  private static final int lowerBound = 100;
  private static final int upperBound = 200;

  private final OrderType type;
  public Order(String name, OrderType type) {
    this.name = name;
    this.type = type;
    this.timeToSleep = ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
  }

  public void execute() throws InterruptedException {
    Thread.sleep(timeToSleep);
  }

  public String getName() {
    return name;
  }

  public OrderType getType() {
    return type;
  }

  @Override
  public String toString() {
    return name;
  }
}
