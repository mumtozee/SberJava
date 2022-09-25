package ru.sber.client;

import ru.sber.util.JsonParser;
import ru.sber.util.JsonKeyValuePair;

import java.util.Objects;

public class LegalEntity extends Client {
  private final String parentName;

  public LegalEntity(String name, long inn, String industry, String parentName) {
    super(name, inn, industry);
    this.parentName = parentName;
  }

  public String getParentName() {
    return parentName;
  }
}
