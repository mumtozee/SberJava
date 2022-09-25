package ru.sber;

import ru.sber.util.JsonParser;
import ru.sber.util.ListParsingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TmpTest {
  public static void main(String[] args) {
    String jsonString = """
            {
              "name": "John Doe",
              "inn": 1234,
              "industry": "software",
              "double_field": 1234.56,
              "list_field": [[23], [35], [56]],
              "list_field_2": [""],
              "bool_field": true,
              "string_list_field": ["", "str1", "str2", "[\"str3\", \"str4\"]"]
            }
            """;
    HashMap<String, String> map = JsonParser.parse(jsonString);
    for (String s : map.keySet()) {
      System.out.println(s + " : " + map.get(s));
    }
    System.out.println("***************************");
    ArrayList<String> parsedList = ListParsingService.parseStringArray(map.get("string_list_field"));
    System.out.println(parsedList.get(0));
    System.out.println(parsedList.get(1));
    System.out.println(parsedList.get(2));
    System.out.println(parsedList.get(3));

    String[] parsedList2 = ListParsingService.parseArray(map.get("list_field_2"));
    System.out.println(parsedList2.length);

    System.out.println("***************************");
    System.out.println(Double.parseDouble(map.get("double_field")));
  }
}
