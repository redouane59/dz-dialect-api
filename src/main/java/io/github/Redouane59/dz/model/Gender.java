package io.github.Redouane59.dz.model;

import io.github.Redouane59.dz.helper.Config;
import java.util.List;
import lombok.Getter;

@Getter
public enum Gender {

  M(List.of(new Translation(Lang.FR, "le"), new Translation(Lang.DZ, "el"))),
  F(List.of(new Translation(Lang.FR, "la"), new Translation(Lang.FR, "el"))),
  X(List.of(new Translation(Lang.FR, "les"), new Translation(Lang.FR, "")));

  private final List<Translation> translations;

  Gender(List<Translation> translations) {
    this.translations = translations;
  }

  public String getTranslationValue(Lang lang, String nextWord) {
    Translation translation = getTranslations().stream().filter(o -> o.getLang() == lang).findAny().orElse(new Translation(lang, ""));
    if (lang == Lang.FR && Config.VOWELS.contains(nextWord.charAt(0)) && this != X) {
      return "l'";
    } else if (lang == Lang.DZ) {
      if (this == X) {
        return "";
      } else if (Config.CONSONANTS.contains(nextWord.substring(0, 1))) {
        return "e" + nextWord.charAt(0);
      }
    }
    return translation.getValue();
  }
}