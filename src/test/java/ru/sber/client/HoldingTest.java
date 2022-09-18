package ru.sber.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HoldingTest {
  private final String jsonString;
  private final Holding client;

  public HoldingTest() throws IOException {
    this.jsonString = Files.readString(Path.of("holding_test.json"));
    this.client = new Holding(this.jsonString);
  }

  @Test
  public void testObjectCreation() {
    Assertions.assertAll(() -> assertEquals(client.getName(), "Alphabet"),
            () -> assertEquals(client.getIndustry(), "software"),
            () -> assertEquals(client.getInn(), "12345"),
            () -> assertEquals(client.getAssetCount(), 23));
  }
}
