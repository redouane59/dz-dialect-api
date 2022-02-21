package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Verb extends AbstractWord {

  private List<Conjugator> conjugators;
  @JsonProperty("possible_complements")
  private List<WordType>   possibleComplements;
  @JsonProperty("verb_type")
  private VerbType         verbType;

  public Optional<Conjugation> getRandomConjugation(List<Tense> tenses) {
    // get all possible conjugation
    List<Conjugator> matchingConjugator = conjugators.stream().filter(o -> tenses.contains(o.getTense())).collect(Collectors.toList());
    if (matchingConjugator.isEmpty()) {
      return Optional.empty();
    }
    int        index      = new Random().nextInt(matchingConjugator.size());
    Conjugator conjugator = matchingConjugator.get(index);
    index = new Random().nextInt(conjugator.getConjugations().size());
    return Optional.of(conjugator.getConjugations().get(index));
  }
}
