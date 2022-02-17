package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.noun.WordType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Verb {

  @JsonProperty("id")
  private String           id;
  @JsonProperty("conjugators")
  private List<Conjugator> conjugators = new ArrayList<>();
  @JsonProperty("complements")
  private List<WordType>   complements = new ArrayList<>();
  private VerbType         type;

  public Conjugation getRandomConjugation(List<Tense> tenses) {
    // get all possible conjugation
    List<Conjugator> matchingConjugator = conjugators.stream().filter(o -> tenses.contains(o.getTense())).collect(Collectors.toList());
    int              index              = new Random().nextInt(matchingConjugator.size());
    Conjugator       conjugator         = matchingConjugator.get(index);
    index = new Random().nextInt(conjugator.getConjugations().size());
    return conjugator.getConjugations().get(index);
  }
}
