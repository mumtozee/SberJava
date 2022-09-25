package ru.sber.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IndividualTest {
  private final String jsonString;
  private final Individual client;

  public IndividualTest() throws IOException {
    this.jsonString = Files.readString(Path.of("indi_test.json"));
    this.client = (Individual) Client.createClient(this.jsonString);
  }

  @Test
  public void testCrimeRecordsField() {
    assertFalse(client.hasCrimeRecord());
  }

  @Test
  public void testInnField() {
    assertEquals(client.getInn(), 1657);
  }

  @Test
  public void testIndustryField() {
    assertEquals(client.getIndustry(), "machinery");
  }

  @Test
  public void testNameField() {
    assertEquals(client.getName(), "Peter Parker");
  }
}
