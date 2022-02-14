package io.github.Redouane59.dz.function.model.noun;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.function.model.Gender;
import io.github.Redouane59.dz.function.model.Lang;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NounRoot {

  List<Noun> nouns;
  @JsonProperty("fr_root")
  private String   frRoot;
  private WordType type;
  private Gender   gender;

  public Optional<Noun> getNoun(boolean singular) {
    return nouns.stream().filter(o -> o.isSingular() == singular).findAny();
  }

  public Optional<String> getNounValue(boolean singular, Lang lang) {
    return getNoun(singular).orElse(new Noun()).getTranslation(lang);
  }


}
