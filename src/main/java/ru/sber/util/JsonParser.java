package ru.sber.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class JsonParser {
  private final Pattern pattern;
  private final Matcher matcher;

  private final String jsonString;

  {
    String patternString = "\"([^\"]+)\"\s*:\s*([0-9]+|\"[^\"]+\")";
    pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
  }

  public JsonParser(String jsonString) {
    this.jsonString = jsonString;
    matcher = pattern.matcher(this.jsonString);
  }

  public Pair getNextKeyValue() {
    if (!matcher.find()) {
      return null;
    }
    return new Pair(matcher.group());
  }
}
