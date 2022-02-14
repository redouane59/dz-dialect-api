package io.github.Redouane59.dz.function.model;

public enum Tense {
  PAST("past"),
  PRESENT("present");

  private final String value;

  Tense(String value) {
    this.value = value;
  }

  public enum Gender {

    M("masculin"),
    F("féminin"),
    X("indéterminé");

    private final String definition;

    Gender(String definition) {
      this.definition = definition;
    }
  }
}
