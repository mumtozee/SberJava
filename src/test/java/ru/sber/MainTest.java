package ru.sber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sber.client.ClientType;

public class MainTest {
  @Test
  public void testClientType() {
    String jsonString = """
            {
              "clientType": "LEGAL_ENTITY"
            }
            """;
    ClientType type = Main.getClientType(jsonString);
    Assertions.assertEquals(type, ClientType.LEGAL_ENTITY);
  }
}
