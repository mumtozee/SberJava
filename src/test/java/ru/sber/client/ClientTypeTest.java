package ru.sber.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientTypeTest {
  private final String indivJson;
  private final String legalEntityJson;
  private final String holdingJson;
  private ClientType type;

  public ClientTypeTest() throws IOException {
    this.legalEntityJson = Files.readString(Path.of("legal_entity_test.json"));
    this.holdingJson = Files.readString(Path.of("holding_test.json"));
    this.indivJson = Files.readString(Path.of("indi_test.json"));
    this.type = null;
  }

  @Test
  public void testObjectInstance() {
    type = ClientType.INDIVIDUAL;
    Client client1 = type.createClient(this.indivJson);
    Assertions.assertInstanceOf(Individual.class, client1);

    type = ClientType.LEGAL_ENTITY;
    Client client2 = type.createClient(this.legalEntityJson);
    Assertions.assertInstanceOf(LegalEntity.class, client2);

    type = ClientType.HOLDING;
    Client client3 = type.createClient(this.holdingJson);
    Assertions.assertInstanceOf(Holding.class, client3);
  }
}
