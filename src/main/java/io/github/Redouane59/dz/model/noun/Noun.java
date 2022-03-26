package io.github.Redouane59.dz.model.noun;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Noun extends AbstractWord {

  @JsonProperty("noun_types")
  private Set<NounType>    nounTypes;
  @JsonProperty("state_preposition")
  private Set<Translation> statePreposition;
  @JsonProperty("deplacement_preposition")
  private Set<Translation> deplacementPreposition;

  public Optional<Translation> getStatePreposition(Lang lang) {
    return statePreposition.stream()
                           .filter(o -> o.getLang() == lang)
                           .findAny();
  }

  public Optional<Translation> getDeplacementProposition(Lang lang) {
    return deplacementPreposition.stream()
                                 .filter(o -> o.getLang() == lang)
                                 .findAny();
  }
}
