package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Conjugator {

  private final List<PossessiveWord> conjugations = new ArrayList<>();
  private       Tense                tense;

  public PossessiveWord getConjugationByCriteria(Gender gender, boolean singular) {
    PossessiveWord result = null;
    for (PossessiveWord subconjugation : conjugations) {
      if (subconjugation.isSingular() == singular && (subconjugation.getGender() == gender || gender == Gender.X)) {
        return subconjugation;
      }
    }
    return result;
  }

}