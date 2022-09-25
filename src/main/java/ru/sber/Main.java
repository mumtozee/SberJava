package ru.sber;

import ru.sber.client.*;
import ru.sber.util.JsonParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    Path filePath = Path.of("holding_test.json");
    try {
      String jsonString = Files.readString(filePath);
      HashMap<String, String> fieldsMap = JsonParser.parse(jsonString);
      ClientType type = JsonParser.getClientType(jsonString);
      if (type == null) {
        System.out.println("No client type given in the file!");
        return;
      }

      // Variant 1
      {
        System.out.println("Variant 1");
        Client client = switch (type) {
          case HOLDING -> new Holding(fieldsMap.get("name"),
                  Long.parseLong(fieldsMap.get("inn")),
                  fieldsMap.get("industry"),
                  Double.parseDouble(fieldsMap.get("meanAssetCount")));
          case INDIVIDUAL -> new Individual(fieldsMap.get("name"),
                  Long.parseLong(fieldsMap.get("inn")),
                  fieldsMap.get("industry"),
                  Boolean.parseBoolean(fieldsMap.get("hasCrimeRecord")));
          case LEGAL_ENTITY -> new LegalEntity(fieldsMap.get("name"),
                  Long.parseLong(fieldsMap.get("inn")),
                  fieldsMap.get("industry"),
                  fieldsMap.get("parentName"));
        };
        System.out.println(client.getName());
        System.out.println(client.getInn());
        System.out.println(client.getIndustry());
        System.out.println(((Holding) client).getMeanAssetCount());
      }

      // Variant 2
      {
        System.out.println("***********\nVariant 2");
        Client client = type.createClient(fieldsMap);
        System.out.println(client.getName());
        System.out.println(client.getInn());
        System.out.println(client.getIndustry());
        System.out.println(((Holding) client).getMeanAssetCount());
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
