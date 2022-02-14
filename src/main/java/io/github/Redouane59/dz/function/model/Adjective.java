package io.github.Redouane59.dz.function.model;

import io.github.Redouane59.dz.function.model.Tense.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Adjective {

  private Gender      gender;
  private boolean     singular;
  private Translation translation;

}
