package io.github.Redouane59.dz.function.model.noun;

import io.github.Redouane59.dz.function.model.AbstractWord;
import io.github.Redouane59.dz.function.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Noun extends AbstractWord {

  private boolean singular;
  private Gender  gender;

}
