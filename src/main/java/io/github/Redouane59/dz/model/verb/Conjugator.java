package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Conjugator {

  private final List<Conjugation> conjugations = new ArrayList<>();
  private       Tense             tense;

  public Optional<Conjugation> getConjugationByCriteria(Gender gender, boolean singular) {

    return conjugations.stream()
                       .filter(o -> o.isSingular() == singular && (o.getGender() == gender || o.getGender() == Gender.X))
                       .findAny();

  }

  public Optional<Conjugation> getConjugationByCriteria(Gender gender, boolean singular, Possession possession) {

    return conjugations.stream()
                       .filter(o -> o.isSingular() == singular
                                    && (o.getGender() == gender || o.getGender() == Gender.X || gender == Gender.X))
                       .filter(o -> o.getPossession() == possession)
                       .findAny();

  }

}