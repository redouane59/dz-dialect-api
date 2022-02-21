package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public enum PersonalProunoun {
  I(List.of(new Translation(Lang.FR, "je"), new Translation(Lang.DZ, "ana"))),
  I2(List.of(new Translation(Lang.FR, "j'"), new Translation(Lang.DZ, "ana"))),
  YOU_M(List.of(new Translation(Lang.FR, "tu"), new Translation(Lang.DZ, "enta"))),
  YOU_F(List.of(new Translation(Lang.FR, "tu"), new Translation(Lang.DZ, "enti"))),
  HE(List.of(new Translation(Lang.FR, "il"), new Translation(Lang.DZ, "houwa"))),
  SHE(List.of(new Translation(Lang.FR, "elle"), new Translation(Lang.DZ, "hiya"))),
  WE(List.of(new Translation(Lang.FR, "on"), new Translation(Lang.DZ, "7na"))),
  YOU_P(List.of(new Translation(Lang.FR, "vous"), new Translation(Lang.DZ, "ntouma"))),
  THEY_M(List.of(new Translation(Lang.FR, "ils"), new Translation(Lang.DZ, "houma"))),
  THEY_F(List.of(new Translation(Lang.FR, "elles"), new Translation(Lang.DZ, "houma"))),
  UNDEFINED(new ArrayList<>());

  private final List<Translation> translations;

  PersonalProunoun(List<Translation> translations) {
    this.translations = translations;
  }

  public String getTranslationValue(Lang lang) {
    return getTranslations()
        .stream().filter(o -> o.getLang() == lang).findAny().get().getValue();
  }

}
