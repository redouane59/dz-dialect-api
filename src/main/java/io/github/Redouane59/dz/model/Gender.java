package io.github.Redouane59.dz.model;

import io.github.Redouane59.dz.helper.Config;
import java.util.List;
import lombok.Getter;

@Getter
public enum Gender {

  M(List.of(new Translation(Lang.FR, "le"), new Translation(Lang.DZ, "el")),
    List.of(new Translation(Lang.FR, "un"), new Translation(Lang.DZ, "wa7ad"))),
  F(List.of(new Translation(Lang.FR, "la"), new Translation(Lang.FR, "el")),
    List.of(new Translation(Lang.FR, "une"), new Translation(Lang.DZ, "wa7ad"))),
  X(List.of(new Translation(Lang.FR, "les"), new Translation(Lang.FR, "")),
    List.of(new Translation(Lang.FR, "des"), new Translation(Lang.DZ, "")));

  private final List<Translation> articleTranslations;
  private final List<Translation> undefinedArticleTranslations;

  Gender(List<Translation> ArticleTranslations, List<Translation> undefinedArticleTranslations) {
    this.articleTranslations          = ArticleTranslations;
    this.undefinedArticleTranslations = undefinedArticleTranslations;
  }

  public String getArticleTranslationValue(Lang lang, String nextWord) {
    Translation translation = this.getArticleTranslations().stream().filter(o -> o.getLang() == lang).findAny().orElse(new Translation(lang, ""));
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

  public String getUndefinedArticleTranslationValue(Lang lang) {
    Translation
        translation =
        this.getUndefinedArticleTranslations().stream().filter(o -> o.getLang() == lang).findAny().orElse(new Translation(lang, ""));
    return translation.getValue();
  }
}