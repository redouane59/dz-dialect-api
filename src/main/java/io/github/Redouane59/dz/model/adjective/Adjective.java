package io.github.Redouane59.dz.model.adjective;

import io.github.Redouane59.dz.model.AbstractWord;
import io.github.Redouane59.dz.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Adjective extends AbstractWord {

  private Gender  gender;
  private boolean singular;

}
