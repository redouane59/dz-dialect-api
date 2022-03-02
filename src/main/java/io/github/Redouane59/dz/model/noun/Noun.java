package io.github.Redouane59.dz.model.noun;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Noun extends AbstractWord {

  @JsonProperty("noun_types")
  private List<NounType> nounTypes;

}
