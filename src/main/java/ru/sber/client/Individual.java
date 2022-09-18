package ru.sber.client;

import ru.sber.util.JsonParser;
import ru.sber.util.Pair;

import java.util.Objects;

public class Individual extends Client {
  private final boolean hasCrimeRecord;

  public Individual(String name, String inn, String industry, boolean hasCrimeRecord) {
    super(name, inn, industry);
    this.hasCrimeRecord = hasCrimeRecord;
  }

  public Individual(String jsonString) {
    super(jsonString);
    JsonParser parser = new JsonParser(jsonString);
    Pair pair = parser.getNextKeyValue();
    boolean hasCrimeRecordField = false;
    while (pair != null) {
      if (Objects.equals(pair.key, "hasCrimeRecord")) {
        hasCrimeRecordField = Boolean.parseBoolean(pair.value);
        break;
      }
      pair = parser.getNextKeyValue();
    }
    this.hasCrimeRecord = hasCrimeRecordField;
  }

  public boolean hasCrimeRecord() {
    return hasCrimeRecord;
  }
}
