package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.Gender;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Conjugator {

  private final List<Conjugation> conjugations = new ArrayList<>();
  private       Tense             tense;

  // @todo to remove
  /* public Conjugator(Tense tense, Translation p1x, Translation p2m, Translation p2f, Translation p3m, Translation p3f,
                    Translation p4x, Translation p5x, Translation p6x) {
    this.tense = tense;
    conjugations.add(new Conjugation(Person.P1, p1x));
    conjugations.add(new Conjugation(Person.P2M, p2m));
    conjugations.add(new Conjugation(Person.P2F, p2f));
    conjugations.add(new Conjugation(Person.P3M, p3m));
    conjugations.add(new Conjugation(Person.P3F, p3f));
    conjugations.add(new Conjugation(Person.P4, p4x));
    conjugations.add(new Conjugation(Person.P5, p5x));
    conjugations.add(new Conjugation(Person.P6, p6x));
  } */

  public Conjugation getConjugationByCriteria(Gender gender, boolean singular) {
    Conjugation result = null;
    for (Conjugation subconjugation : conjugations) {
      if (subconjugation.getPerson().isSingular() == singular && (subconjugation.getPerson().getGender() == gender || gender == Gender.X)) {
        return subconjugation;
      }
    }
    return result;
  }

}