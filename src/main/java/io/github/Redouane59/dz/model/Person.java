package io.github.Redouane59.dz.model;

import lombok.Getter;

@Getter
public enum Person {

  P1(Possession.I, Gender.X, true),
  P2M(Possession.YOU, Gender.M, true),
  P2F(Possession.YOU, Gender.F, true),
  P3M(Possession.OTHER, Gender.M, true),
  P3F(Possession.OTHER, Gender.F, true),
  P4(Possession.I, Gender.X, false),
  P5(Possession.YOU, Gender.X, false),
  P6(Possession.OTHER, Gender.X, false);

  private final Possession possession;
  private final Gender     gender;
  private final boolean    singular;

  Person(Possession possession, Gender gender, boolean singular) {
    this.possession = possession;
    this.gender     = gender;
    this.singular   = singular;
  }
}
