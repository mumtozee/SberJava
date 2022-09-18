package ru.sber.util;

public class Pair {
  public final String key;
  public final String value;

  public Pair(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public Pair(String jsonLine) {
    String[] splitted = jsonLine.split("\"\s*:\s*\"*");
    String key = splitted[0].substring(1); // the key always starts with ", so remove it
    String value = splitted[1];
    if (splitted[1].endsWith("\"")) {
      value = value.substring(0, value.length() - 1);
    }
    this.key = key;
    this.value = value;
  }
}
