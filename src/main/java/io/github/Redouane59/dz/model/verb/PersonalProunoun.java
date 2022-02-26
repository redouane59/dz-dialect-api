package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;

@Getter
public enum PersonalProunoun {
  I(Possession.I, true, Gender.X, List.of(new Translation(Lang.FR, "je"), new Translation(Lang.DZ, "ana"))),
  YOU_M(Possession.YOU, true, Gender.M, List.of(new Translation(Lang.FR, "tu"), new Translation(Lang.DZ, "enta"))),
  YOU_F(Possession.YOU, true, Gender.F, List.of(new Translation(Lang.FR, "tu"), new Translation(Lang.DZ, "enti"))),
  HE(Possession.OTHER, true, Gender.M, List.of(new Translation(Lang.FR, "il"), new Translation(Lang.DZ, "houwa"))),
  SHE(Possession.OTHER, true, Gender.F, List.of(new Translation(Lang.FR, "elle"), new Translation(Lang.DZ, "hiya"))),
  WE(Possession.I, false, Gender.X, List.of(new Translation(Lang.FR, "on"), new Translation(Lang.DZ, "7na"))),
  YOU_P(Possession.YOU, false, Gender.X, List.of(new Translation(Lang.FR, "vous"), new Translation(Lang.DZ, "ntouma"))),
  THEY_M(Possession.OTHER, false, Gender.M, List.of(new Translation(Lang.FR, "ils"), new Translation(Lang.DZ, "houma"))),
  THEY_F(Possession.OTHER, false, Gender.F, List.of(new Translation(Lang.FR, "elles"), new Translation(Lang.DZ, "houma"))),
  UNDEFINED(Possession.OTHER, true, Gender.X, new ArrayList<>());

  private final Possession        possession;
  private final boolean           singular;
  private final Gender            gender;
  private final List<Translation> translations;

  PersonalProunoun(Possession possession, boolean singular, Gender gender, List<Translation> translations) {
    this.possession   = possession;
    this.translations = translations;
    this.singular     = singular;
    this.gender       = gender;
  }

  public static PersonalProunoun getRandomPersonalPronoun() {
    return PersonalProunoun.values()[new Random().nextInt(PersonalProunoun.values().length - 1)];
  }

  public String getTranslationValue(Lang lang) {
    return getTranslations()
        .stream().filter(o -> o.getLang() == lang).findAny().get().getValue();
  }
}
