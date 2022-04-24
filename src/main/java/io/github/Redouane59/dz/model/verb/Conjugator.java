package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Conjugator {

  private final Set<Conjugation> conjugations = new HashSet<>();
  private       Tense            tense;

  public Optional<Conjugation> getConjugationByCriteria(Gender gender, boolean singular, Possession possession) {

    return conjugations.stream()
                       .filter(o -> o.isSingular() == singular)
                       .filter(o -> o.getPossession() == possession || this.getTense() == Tense.IMPERATIVE)
                       .filter(o -> o.getGender() == gender || o.getGender() == Gender.X || gender == Gender.X ||
                                    (gender == Gender.F && !singular))
                       .findAny();

  }

}