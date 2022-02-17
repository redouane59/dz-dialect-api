package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.CustomList;
import io.github.Redouane59.dz.model.Person;
import io.github.Redouane59.dz.model.AbstractWord;
import io.github.Redouane59.dz.model.Translation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Conjugation extends AbstractWord {

  private Person person;

  public Conjugation(Person person, Translation fr, Translation dz) {
    super(CustomList.of(fr, dz));
    this.person = person;
  }

}
