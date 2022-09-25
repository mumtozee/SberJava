package ru.sber.client;

import ru.sber.util.JsonParser;
import ru.sber.util.JsonKeyValuePair;

import java.util.Objects;

public class Holding extends Client {
  private final double meanAssetCount;

  public Holding(String name, long inn, String industry, double meanAssetCount) {
    super(name, inn, industry);
    this.meanAssetCount = meanAssetCount;
  }

  public double getMeanAssetCount() {
    return meanAssetCount;
  }
}
