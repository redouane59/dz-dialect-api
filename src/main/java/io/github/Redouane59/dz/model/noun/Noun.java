package io.github.Redouane59.dz.model.noun;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Word;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Noun extends AbstractWord {

  @JsonProperty("noun_types")
  private Set<NounType> nounTypes;
  // @todo use enum instead of direct word to avoid duplication
  @JsonProperty("state_preposition")
  private Word          statePreposition;
  @JsonProperty("deplacement_preposition")
  private Word          deplacementPreposition; // @todo dirty


}
