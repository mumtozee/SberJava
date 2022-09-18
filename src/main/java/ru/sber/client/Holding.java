package ru.sber.client;

import ru.sber.util.JsonParser;
import ru.sber.util.Pair;

import java.util.Objects;

public class Holding extends Client {
  private final int assetCount;

  public Holding(String name, String inn, String industry, int assetCount) {
    super(name, inn, industry);
    this.assetCount = assetCount;
  }

  public Holding(String jsonString) {
    super(jsonString);
    JsonParser parser = new JsonParser(jsonString);
    Pair pair = parser.getNextKeyValue();
    int assetCountField = -1;
    while (pair != null) {
      if (Objects.equals(pair.key, "assetCount")) {
        assetCountField = Integer.parseInt(pair.value);
        break;
      }
      pair = parser.getNextKeyValue();
    }
    this.assetCount = assetCountField;
  }

  public int getAssetCount() {
    return assetCount;
  }
}
