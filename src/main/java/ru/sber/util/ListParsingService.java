package ru.sber.util;

import java.util.ArrayList;

public class ListParsingService {
  public static String[] parseArray(String listRepr) {
    String trimmedListRepr = removeSquareBrackets(listRepr);
    if (trimmedListRepr.length() == 0) {
      return new String[]{};
    }
    return trimmedListRepr.split("(\s)*,(\s)*");
  }

  public static ArrayList<String> parseStringArray(String listRepr) {
    ArrayList<String> result = new ArrayList<>();
    String trimmedListRepr = removeSquareBrackets(listRepr);
    if (trimmedListRepr.length() == 0) {
      return result;
    }
    int doubleQuoteCount = 0;
    int itemStartIndex = 1;
    final int HOP_SIZE = 4;
    for (int i = 0; i < trimmedListRepr.length() - 1; ++i) {
      char currChar = trimmedListRepr.charAt(i);
      if (currChar == '\"') {
        ++doubleQuoteCount;
        char nextChar = trimmedListRepr.charAt(i + 1);
        if (nextChar == ',') {
          if (doubleQuoteCount % 2 == 0) {
            result.add(trimmedListRepr.substring(itemStartIndex, i));
            itemStartIndex = i + HOP_SIZE;
          }
        }
      }
    }
    result.add(trimmedListRepr.substring(itemStartIndex, trimmedListRepr.length() - 1));
    return result;
  }

  private static String removeSquareBrackets(String listRepr) {
    StringBuilder stringBuilder = new StringBuilder(listRepr);
    stringBuilder.deleteCharAt(listRepr.length() - 1);
    stringBuilder.deleteCharAt(0);
    return stringBuilder.toString();
  }
}
