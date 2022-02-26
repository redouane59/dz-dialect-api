package io.github.Redouane59.dz.model;

import java.util.List;

public enum PossessiveAdjective {
  MY_M(Possession.I, Gender.M, true, List.of()),
  MY_F(Possession.I, Gender.F, true, List.of()),
  YOUR_M(Possession.YOU, Gender.M, true, List.of()),
  YOUR_F(Possession.YOU, Gender.F, true, List.of()),
  HIS_M(Possession.OTHER, Gender.M, true, List.of()),
  HIS_F(Possession.OTHER, Gender.M, true, List.of()),
  OUR(Possession.I, Gender.X, false, List.of()),
  YOUR_P(Possession.YOU, Gender.X, false, List.of()),
  THEIR(Possession.OTHER, Gender.X, false, List.of());

  private final Possession        possession;
  private final Gender            gender;
  private final boolean           singular;
  private final List<Translation> translations;

  PossessiveAdjective(Possession possession, Gender gender, boolean singular, List<Translation> translations) {
    this.possession   = possession;
    this.gender       = gender;
    this.singular     = singular;
    this.translations = translations;
  }
}
