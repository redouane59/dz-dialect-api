package io.github.Redouane59.dz.function.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Adjectivator {

  List<Adjective> adjectives = new ArrayList<>();

  public Adjectivator(Translation m, Translation f, Translation p) {
    adjectives.add(new Adjective(Gender.M, true, m));
    adjectives.add(new Adjective(Gender.F, true, f));
    adjectives.add(new Adjective(Gender.X, false, p));

  }

  public Adjective getAdjectiveByCriterion(Gender gender, boolean singular) {
    Adjective result = null;
    for (Adjective adjective : adjectives) {
      if (adjective.isSingular() == singular && (adjective.getGender() == gender || gender == Gender.X)) {
        return adjective;
      }
    }
    return result;
  }
}
