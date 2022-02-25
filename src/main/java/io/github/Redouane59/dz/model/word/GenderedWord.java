package io.github.Redouane59.dz.model.word;

import io.github.Redouane59.dz.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GenderedWord extends Word {

  private Gender  gender;
  private boolean singular;


}
