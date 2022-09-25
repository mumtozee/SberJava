package ru.sber.client;

import ru.sber.util.JsonParser;
import ru.sber.util.JsonKeyValuePair;

import java.util.HashMap;

public abstract class Client {
  private final String name;
  private final long inn;
  private final String industry;

  public Client(String name, long inn, String industry) {
    this.inn = inn;
    this.name = name;
    this.industry = industry;
  }

  public String getName() {
    return name;
  }

  public long getInn() {
    return inn;
  }

  public String getIndustry() {
    return industry;
  }

  public static Client createClient(String jsonString) {
    ClientType clientType = JsonParser.getClientType(jsonString);
    if (clientType == null) {
      return null;
    }
    HashMap<String, String> classFields = JsonParser.parse(jsonString);
    return clientType.createClient(classFields);
  }
}
