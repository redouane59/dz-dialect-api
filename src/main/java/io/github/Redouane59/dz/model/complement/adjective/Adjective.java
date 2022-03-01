package io.github.Redouane59.dz.model.complement.adjective;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Adjective extends AbstractWord {

  @JsonProperty("possible_nouns")
  List<WordType> possibleNouns;
}
