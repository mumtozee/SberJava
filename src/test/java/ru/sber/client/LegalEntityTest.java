package ru.sber.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LegalEntityTest {
  private final String jsonString;
  private final LegalEntity client;

  public LegalEntityTest() throws IOException {
    this.jsonString = Files.readString(Path.of("legal_entity_test.json"));
    this.client = (LegalEntity) Client.createClient(this.jsonString);
  }

  @Test
  public void testNameField() {
    assertEquals(client.getName(), "Boeing");
  }

  @Test
  public void testIndustryField() {
    assertEquals(client.getIndustry(), "aircraft");
  }

  @Test
  public void testInnField() {
    assertEquals(client.getInn(), 1234);
  }

  @Test
  public void testParentNameField() {
    assertEquals(client.getParentName(), "Aeroplane holdings");
  }
}
