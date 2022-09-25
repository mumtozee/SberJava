package ru.sber.client;

import java.util.HashMap;

public enum ClientType {
  INDIVIDUAL {
    @Override
    public Individual createClient(HashMap<String, String> fieldsMap) {
      return new Individual(fieldsMap.get("name"),
                            Long.parseLong(fieldsMap.get("inn")),
                            fieldsMap.get("industry"),
                            Boolean.parseBoolean(fieldsMap.get("hasCrimeRecord")));
    }
  },
  LEGAL_ENTITY {
    @Override
    public LegalEntity createClient(HashMap<String, String> fieldsMap) {
      return new LegalEntity(fieldsMap.get("name"),
              Long.parseLong(fieldsMap.get("inn")),
              fieldsMap.get("industry"),
              fieldsMap.get("parentName"));
    }
  },
  HOLDING {
    @Override
    public Holding createClient(HashMap<String, String> fieldsMap) {
      return new Holding(fieldsMap.get("name"),
              Long.parseLong(fieldsMap.get("inn")),
              fieldsMap.get("industry"),
              Double.parseDouble(fieldsMap.get("meanAssetCount")));
    }
  };

  public abstract Client createClient(HashMap<String, String> fieldsMap);
}
