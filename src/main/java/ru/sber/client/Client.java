package ru.sber.client;

import ru.sber.util.JsonParser;
import ru.sber.util.Pair;

public abstract class Client {
  private final String name;
  private final String inn;
  private final String industry;

  public Client(String name, String inn, String industry) {
    this.inn = inn;
    this.name = name;
    this.industry = industry;
  }

  public Client(String jsonString) {
    JsonParser parser = new JsonParser(jsonString);
    Pair pair = parser.getNextKeyValue();
    String nameField = null;
    String innField = null;
    String industryField = null;

    while (pair != null) {
      switch (pair.key) {
        case "name" -> nameField = pair.value;
        case "inn" -> innField = pair.value;
        case "industry" -> industryField = pair.value;
      }

      if (nameField == null || innField == null || industryField == null) {
        pair = parser.getNextKeyValue();
      } else {
        break;
      }
    }
    this.name = nameField;
    this.inn = innField;
    this.industry = industryField;
  }

  public String getName() {
    return name;
  }

  public String getInn() {
    return inn;
  }

  public String getIndustry() {
    return industry;
  }
}
