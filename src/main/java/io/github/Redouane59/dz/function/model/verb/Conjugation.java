package io.github.Redouane59.dz.function.model.verb;

import io.github.Redouane59.dz.function.model.AbstractWord;
import io.github.Redouane59.dz.function.model.Person;
import io.github.Redouane59.dz.function.model.Translation;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Conjugation extends AbstractWord {

  private Person person;

  public Conjugation(Person person, Translation fr, Translation dz) {
    super(List.of(fr, dz));
    this.person = person;
  }

}
