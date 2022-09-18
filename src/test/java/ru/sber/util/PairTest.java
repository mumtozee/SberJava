package ru.sber.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PairTest {
  @Test
  public void testKeyValue() {
    String keyVal = "\"some_key\": \"some_val\"";
    Pair pair = new Pair(keyVal);
    Assertions.assertAll(() -> assertEquals(pair.key, "some_key"),
            () -> assertEquals(pair.value, "some_val"));
  }
}
