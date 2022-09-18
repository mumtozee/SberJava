package ru.sber.client;

public enum ClientType {
  INDIVIDUAL {
    @Override
    public Individual createClient(String jsonString) {
      return new Individual(jsonString);
    }
  },
  LEGAL_ENTITY {
    @Override
    public LegalEntity createClient(String jsonString) {
      return new LegalEntity(jsonString);
    }
  },
  HOLDING {
    @Override
    public Holding createClient(String jsonString) {
      return new Holding(jsonString);
    }
  };

  public abstract Client createClient(String jsonString);
}
