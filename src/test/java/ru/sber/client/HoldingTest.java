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
    this.client = (Holding) Client.createClient(this.jsonString);
  }

  @Test
  public void testNameField() {
    assertEquals(client.getName(), "Alphabet");
  }

  @Test
  public void testIndustryField() {
    assertEquals(client.getIndustry(), "software");
  }

  @Test
  public void testInnField() {
    assertEquals(client.getInn(), 12345);
  }

  @Test
  public void testMeanAssetCountField() {
    assertEquals(client.getMeanAssetCount(), 23.34);
  }
}
