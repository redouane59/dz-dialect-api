package io.github.Redouane59.dz.model.word;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AbstractWord {

  @JsonInclude(Include.NON_EMPTY)
  private List<GenderedWord> values = new ArrayList<>();
  private String             id;
  @JsonProperty("word_type")
  private WordType           wordType;

  public String getTranslationValueByGender(Gender gender, boolean isSingular, Lang lang) {
    return getTranslationByGender(gender, isSingular, lang).getValue();
  }

  public Translation getTranslationByGender(Gender gender, boolean isSingular, Lang lang) {
    return getValues().stream()
                      .filter(o -> o.isSingular() == isSingular)
                      .filter(o -> (o.getGender(lang) == gender || gender == Gender.X || o.getGender(lang) == Gender.X))
                      .map(o -> o.getTranslationByLang(lang).get())
                      .findAny()
                      .orElse(new Translation(lang, ""));
  }

  public GenderedWord getWordBySingular(boolean isSingular) {
    return getValues().stream()
                      .filter(o -> o.isSingular() == isSingular)
                      .findAny()
                      .orElseThrow();
  }

  public Optional<GenderedWord> getWordByGenderAndSingular(Gender gender, Lang lang, boolean isSingular) {
    Optional<GenderedWord> result = getValues().stream()
                                               .filter(o -> o.getGender(lang) == gender || o.getGender(lang) == Gender.X)
                                               .filter(o -> o.isSingular() == isSingular)
                                               .findAny();
    if (result.isEmpty()) {
      System.err.println("no word found");
      return Optional.empty();
    }
    return result;
  }

  public Optional<Translation> getTranslationBySingular(boolean isSingular, Lang lang) {
    return getValues().stream()
                      .filter(o -> o.isSingular() == isSingular)
                      .map(o -> o.getTranslationByLang(lang).get())
                      .findAny();
  }

  public String getDzValue(Gender gender, boolean isSingular) {
    return this.getTranslationByGender(gender, isSingular, Lang.DZ).getValue();
  }

  public String getFrValue(Gender gender, boolean isSingular) {
    return this.getTranslationByGender(gender, isSingular, Lang.FR).getValue();
  }

}
