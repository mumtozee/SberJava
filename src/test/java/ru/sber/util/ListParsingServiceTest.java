package ru.sber.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListParsingServiceTest {
  @Test
  public void testParseArray() {
    String arrayRepr = "[23, 56, 76]";
    String[] parsedArray = ListParsingService.parseArray(arrayRepr);
    int[] convertedArray = new int[parsedArray.length];
    for (int i = 0; i < convertedArray.length; i++) {
      convertedArray[i] = Integer.parseInt(parsedArray[i]);
    }
    assertArrayEquals(convertedArray, new int[]{23, 56, 76});
  }

  @Test
  public void testParseEmptyArray() {
    String arrayRepr = "[]";
    String[] parsedArray = ListParsingService.parseArray(arrayRepr);
    assertEquals(parsedArray.length, 0);
  }

  @Test
  public void testParseStringArray() {
    String arrayRepr = "[\"\", \"str1\", \"[\"str2\", \"str3\"]\"]";
    ArrayList<String> parsedArray = ListParsingService.parseStringArray(arrayRepr);
    ArrayList<String> goldArray = new ArrayList<>(Arrays.asList("", "str1", "[\"str2\", \"str3\"]"));
    assertEquals(parsedArray, goldArray);
  }

  @Test
  public void testParseEmptyStringArray() {
    String arrayRepr = "[]";
    ArrayList<String> parsedArray = ListParsingService.parseStringArray(arrayRepr);
    assertEquals(parsedArray.size(), 0);
  }
}
