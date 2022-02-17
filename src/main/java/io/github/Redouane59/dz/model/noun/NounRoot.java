package io.github.Redouane59.dz.model.noun;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NounRoot {

  private final Gender   gender = Gender.X;
  private final WordType type   = WordType.UNDEFINED;
  List<Noun> nouns = new ArrayList<>();
  @JsonProperty("id")
  private String id;

  public Optional<Noun> getNoun(boolean singular) {
    return nouns.stream().filter(o -> o.isSingular() == singular).findAny();
  }

  public Optional<String> getNounValue(boolean singular, Lang lang) {
    return getNoun(singular).orElse(new Noun()).getTranslation(lang);
  }


}
