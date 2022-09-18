package ru.sber.client;

import ru.sber.util.JsonParser;
import ru.sber.util.Pair;

import java.util.Objects;

public class LegalEntity extends Client {
  private final String parentName;

  public LegalEntity(String name, String inn, String industry, String parentName) {
    super(name, inn, industry);
    this.parentName = parentName;
  }

  public LegalEntity(String jsonString) {
    super(jsonString);
    JsonParser parser = new JsonParser(jsonString);
    Pair pair = parser.getNextKeyValue();
    String parentNameField = null;
    while (pair != null) {
      if (Objects.equals(pair.key, "parentName")) {
        parentNameField = pair.value;
        break;
      }
      pair = parser.getNextKeyValue();
    }
    this.parentName = parentNameField;
  }

  public String getParentName() {
    return parentName;
  }
}
