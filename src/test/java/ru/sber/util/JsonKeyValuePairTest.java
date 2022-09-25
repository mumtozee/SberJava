package ru.sber.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonKeyValuePairTest {
  private final String keyVal = "\"some_key\": \"some_val\"";
  private final JsonKeyValuePair pair = JsonKeyValuePair.fromJsonLine(keyVal);

  @Test
  public void testKey() {
    assertEquals(pair.key, "some_key");
  }

  @Test
  public void testValue() {
    assertEquals(pair.value, "some_val");
  }
}
