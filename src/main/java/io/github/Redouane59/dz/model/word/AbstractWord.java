package io.github.Redouane59.dz.model.word;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AbstractWord {

  private final List<GenderedWord> values = new ArrayList<>();
  private       String             id;
  @JsonProperty("word_type")
  private       WordType           wordType;

  public Translation getTranslationByGender(Gender gender, boolean isSingular, Lang lang) {
    return getValues().stream().filter(o -> (o.getGender() == gender) || (gender == Gender.X && o.getGender() == Gender.M))
                      .filter(o -> o.isSingular() == isSingular)
                      .map(o -> o.getTranslationByLang(lang).get())
                      .findAny()
                      .orElseThrow();
  }

  public GenderedWord getWordBySingular(boolean isSingular, Lang lang) {
    return getValues().stream()
                      .filter(o -> o.isSingular() == isSingular)
                      .findAny()
                      .orElseThrow();
  }

  public Translation getTranslationBySingular(boolean isSingular, Lang lang) {
    return getValues().stream()
                      .filter(o -> o.isSingular() == isSingular)
                      .map(o -> o.getTranslationByLang(lang).get())
                      .findAny()
                      .orElseThrow();
  }

  public String getFrValue(Gender gender, boolean isSingular) {
    return this.getTranslationByGender(gender, isSingular, Lang.FR).getValue();
  }

  public String getDzValue(Gender gender, boolean isSingular) {
    return this.getTranslationByGender(gender, isSingular, Lang.DZ).getValue();
  }


}
