package ru.sber.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonParserTest {
  @Test
  public void testNewKeyVal() {
    String jsonString = "{ \"key_1\": \"val_1\", \"key_2\": 234 }";
    JsonParser parser = new JsonParser(jsonString);
    Pair pair1 = parser.getNextKeyValue();
    Assertions.assertAll(() -> assertEquals(pair1.key, "key_1"),
            () -> assertEquals(pair1.value, "val_1"));

    Pair pair2 = parser.getNextKeyValue();
    Assertions.assertAll(() -> assertEquals(pair2.key, "key_2"),
            () -> assertEquals(pair2.value, "234"));
  }
}
