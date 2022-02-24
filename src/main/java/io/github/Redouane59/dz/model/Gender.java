package io.github.Redouane59.dz.model;

import java.util.List;
import lombok.Getter;

@Getter
public enum Gender {

  M(List.of(new Translation(Lang.FR, "le"))),
  F(List.of(new Translation(Lang.FR, "la"))),
  X(List.of(new Translation(Lang.FR, "les")));

  private final List<Translation> translations;

  Gender(List<Translation> translations) {
    this.translations = translations;
  }

  public String getTranslationValue(Lang lang) {
    return getTranslations()
        .stream().filter(o -> o.getLang() == lang).findAny().orElse(new Translation(lang, "")).getValue();
  }
}