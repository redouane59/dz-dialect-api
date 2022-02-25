package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.Gender;
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
                       .filter(o -> o.isSingular() == singular && (o.getGender() == gender || gender == Gender.X))
                       .findAny();

  }

}