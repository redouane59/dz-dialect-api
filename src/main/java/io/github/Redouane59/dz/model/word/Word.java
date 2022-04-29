package io.github.Redouane59.dz.model.word;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Word {

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Translation> translations = new ArrayList<>(); // @todo set instead

  public Optional<Translation> getTranslationByLang(Lang lang) {
    for (Translation t : translations) {
      if (t.getLang() == lang) {
        return Optional.of(t);
      }
    }
    return Optional.empty();
  }

  public String getTranslationValue(Lang lang) {
    return getTranslationByLang(lang).orElse(new Translation(lang, "")).getValue();
  }

  // @todo mix with other method cleanValue
  public String cleanString(String value) {
    if (value == null) {
      return value;
    }
    if (value.startsWith(" ")) {
      value = value.substring(1);
    }
    if (value.endsWith(" ")) {
      value = value.substring(0, value.length() - 1);
    }
    return value.replace("' ", "'").replace("  ", " ");
  }

  @JsonProperty("dz_value")
  public String getDzTranslation() {
    return getTranslationValue(Lang.DZ);
  }

  @JsonProperty("dz_value_ar")
  public String getDzTranslationAr() {
    return (getTranslationByLang(Lang.DZ).orElse(new Translation(Lang.DZ, "", ""))).getArValue();
  }

  @Deprecated
  public void addDzTranslation(String value, String arValue) {
    this.translations.add(new Translation(Lang.DZ, cleanString(value), arValue));
  }

  @JsonProperty("fr_value")
  public String getFrTranslation() {
    return getTranslationValue(Lang.FR);
  }

  @Deprecated
  public void addFrTranslation(String value) {
    this.translations.add(new Translation(Lang.FR, cleanString(value)));
  }

  public void addTranslation(Lang lang, String value) {
    this.translations.add(new Translation(lang, cleanString(value)));
  }

  public void addTranslation(Lang lang, String value, String arValue) {
    this.translations.add(new Translation(lang, cleanString(value), cleanString(arValue)));
  }

  @Override
  public String toString() {
    return this.getTranslationValue(Lang.DZ) + " | " + this.getDzTranslationAr() + " -> " + this.getTranslationValue(Lang.FR);
  }

}
