package ru.sber.client;

import ru.sber.util.JsonParser;
import ru.sber.util.JsonKeyValuePair;

import java.util.Objects;

public class Individual extends Client {
  private final boolean hasCrimeRecord;

  public Individual(String name, long inn, String industry, boolean hasCrimeRecord) {
    super(name, inn, industry);
    this.hasCrimeRecord = hasCrimeRecord;
  }

  public boolean hasCrimeRecord() {
    return hasCrimeRecord;
  }
}
