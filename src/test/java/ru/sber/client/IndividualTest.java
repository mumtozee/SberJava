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
    this.client = new Individual(this.jsonString);
  }

  @Test
  public void testObjectCreation() {
    Assertions.assertAll(() -> assertEquals(client.getName(), "Peter Parker"),
            () -> assertEquals(client.getIndustry(), "machinery"),
            () -> assertEquals(client.getInn(), "1657"),
            () -> assertFalse(client.hasCrimeRecord()));
  }
}
