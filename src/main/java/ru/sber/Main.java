package ru.sber;

import ru.sber.client.*;
import ru.sber.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  public static void main(String[] args) throws IOException {
    Path filePath = Path.of("test.json");
    try {
      String jsonString = Files.readString(filePath);
      ClientType type = getClientType(jsonString);
      if (type == null) {
        System.out.println("No client type given in the file!");
        return;
      }

      // Variant 1
      {
        System.out.println("Variant 1");
        Client client = switch (type) {
          case HOLDING -> new Holding(jsonString);
          case INDIVIDUAL -> new Individual(jsonString);
          case LEGAL_ENTITY -> new LegalEntity(jsonString);
        };
        System.out.println(client.getName());
        System.out.println(client.getInn());
        System.out.println(client.getIndustry());
        System.out.println(((Holding) client).getAssetCount());
      }

      // Variant 2
      {
        System.out.println("***********\nVariant 2");
        Client client = type.createClient(jsonString);
        System.out.println(client.getName());
        System.out.println(client.getInn());
        System.out.println(client.getIndustry());
        System.out.println(((Holding) client).getAssetCount());
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static ClientType getClientType(String jsonString) {
    Pattern ptn = Pattern.compile("\"clientType\"\\s*:\\s*(\"[^\"]+\")", Pattern.CASE_INSENSITIVE);
    Matcher matcher = ptn.matcher(jsonString);
    if (!matcher.find()) {
      return null;
    }
    Pair pair = new Pair(matcher.group());
    return switch (pair.value) {
      case "LEGAL_ENTITY" -> ClientType.LEGAL_ENTITY;
      case "INDIVIDUAL" -> ClientType.INDIVIDUAL;
      case "HOLDING" -> ClientType.HOLDING;
      default -> null;
    };
  }
}
