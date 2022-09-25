package ru.sber.util;

import ru.sber.client.ClientType;

import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class JsonParser {
  public static HashMap<String, String> parse(String jsonString) {
    String patternString = "\"([^\"]+)\"\s*:\s*((\\d+(\\.\\d+)?)|(\".*\")|(true|false)|(\\[.*\\]))" ;
//    Matcher intMatcher = createMatcher("\"([^\"]+)\"\s*:\s*(\\d+)", jsonString);
//    Matcher doubleMatcher = createMatcher("\"([^\"]+)\"\s*:\s*(\\d+\\.\\d+)", jsonString);
//    Matcher boolMatcher = createMatcher("\"([^\"]+)\"\s*:\s*(true|false)", jsonString);
//    Matcher stringMatcher = createMatcher("\"([^\"]+)\"\s*:\s*(\"[.]*\")", jsonString);
//    Matcher arrayMatcher = createMatcher("\"([^\"]+)\"\s*:\s*(\\[[.]*\\])", jsonString);
//    Matcher stringArrayMatcher = createMatcher("\"([^\"]+)\"\s*:\s*(\\[\"[.]*\"\\])", jsonString);
    Matcher matcher = createMatcher(patternString, jsonString);
    HashMap<String, String> result = new HashMap<>();
    while (matcher.find()) {
      JsonKeyValuePair keyValuePair = JsonKeyValuePair.fromJsonLine(matcher.group());
      result.put(keyValuePair.key, keyValuePair.value);
    }
    return result;
  }

  public static ClientType getClientType(String jsonString) {
    Matcher matcher = createMatcher("\"clientType\"\\s*:\\s*(\"[^\"]+\")", jsonString);
    if (!matcher.find()) {
      return null;
    }
    JsonKeyValuePair pair = JsonKeyValuePair.fromJsonLine(matcher.group());
    return switch (pair.value) {
      case "LEGAL_ENTITY" -> ClientType.LEGAL_ENTITY;
      case "INDIVIDUAL" -> ClientType.INDIVIDUAL;
      case "HOLDING" -> ClientType.HOLDING;
      default -> null;
    };
  }

  private static Matcher createMatcher(String regex, String jsonString) {
    return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(jsonString);
  }
}
