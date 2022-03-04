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
public abstract class Word {

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Translation> translations = new ArrayList<>();

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

  @JsonProperty("fr_value")
  public String getFrTranslation() {
    return getTranslationValue(Lang.FR);
  }

  public void addFrTranslation(String value) {
    this.translations.add(new Translation(Lang.FR, cleanString(value)));
  }

  public String cleanString(String value) {
    return value.replace("' ", "'").replace("  ", " ");
  }

  @JsonProperty("dz_value")
  public String getDzTranslation() {
    return getTranslationValue(Lang.DZ);
  }

  public void addDzTranslation(String value) {
    this.translations.add(new Translation(Lang.DZ, cleanString(value)));
  }

  @Override
  public String toString() {
    return this.getTranslationValue(Lang.DZ) + " -> " + this.getTranslationValue(Lang.FR);
  }

}
