package ru.sber.util;

import org.junit.jupiter.api.Test;
import ru.sber.client.ClientType;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonParserTest {
  private final String jsonString = """
            {
              "name": "John Doe",
              "inn": 1234,
              "industry": "software",
              "double_field": 1234.56,
              "list_field": [[23], [35], [56]],
              "list_field_2": [],
              "bool_field": true,
              "string_list_field": ["", "str1", "str2", "[\"str3\", \"str4\"]"]
            }
            """;
  private final HashMap<String, String> parsedMap = JsonParser.parse(jsonString);

  @Test
  public void testParserString() {
    assertEquals(parsedMap.get("name"), "John Doe");
  }

  @Test
  public void testParserInt() {
    assertEquals(Integer.parseInt(parsedMap.get("inn")), 1234);
  }

  @Test
  public void testParserDouble() {
    assertEquals(Double.parseDouble(parsedMap.get("double_field")), 1234.56);
  }

  @Test
  public void testParserBool() {
    assertTrue(Boolean.parseBoolean(parsedMap.get("bool_field")));
  }

  @Test
  public void testParserArray() {
    assertEquals(parsedMap.get("list_field"), "[[23], [35], [56]]");
  }

  @Test
  public void testParserEmptyArray() {
    assertEquals(parsedMap.get("list_field_2"), "[]");
  }

  @Test
  public void testClientType() {
    String jsonString = """
            {
              "clientType": "LEGAL_ENTITY"
            }
            """;
    ClientType type = JsonParser.getClientType(jsonString);
    assertEquals(type, ClientType.LEGAL_ENTITY);
  }
}
