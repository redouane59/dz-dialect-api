package io.github.Redouane59.dz.model.word;

import io.github.Redouane59.dz.model.verb.Tense;
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

}
