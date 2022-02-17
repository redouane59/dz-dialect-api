package io.github.Redouane59.dz.model.noun;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.AbstractWord;
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
