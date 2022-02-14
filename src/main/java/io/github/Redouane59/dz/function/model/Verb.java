package io.github.Redouane59.dz.function.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Verb {

  @JsonProperty("fr_value")
  private String           frValue;
  @JsonProperty("conjugators")
  private List<Conjugator> conjugators = new ArrayList<>();

  public Conjugation getRandomConjugation() {
    int        index      = new Random().nextInt(conjugators.size());
    Conjugator conjugator = conjugators.get(index);
    index = new Random().nextInt(conjugator.getConjugations().size());
    return conjugator.getConjugations().get(index);
  }
}
