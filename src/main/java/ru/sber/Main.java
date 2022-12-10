package ru.sber;

import ru.sber.royaltaxi.Dispatcher;
import ru.sber.royaltaxi.DispatcherImpl;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    Dispatcher d = new DispatcherImpl("Yandex.Taxi", 12, 5);
    d.run();
  }
}