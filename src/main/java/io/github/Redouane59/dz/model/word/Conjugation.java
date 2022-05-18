package io.github.Redouane59.dz.model.word;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.verb.Tense;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Conjugation extends PossessiveWord {

  private Tense tense;
  private int   index;

  public Conjugation(Set<Translation> translations, Gender gender, boolean singular, Possession possession, Tense tense, int index) {
    super(translations, gender, singular, possession);
    this.tense = tense;
    this.index = index;
  }

}
