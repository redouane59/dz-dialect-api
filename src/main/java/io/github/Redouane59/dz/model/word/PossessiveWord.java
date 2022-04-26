package io.github.Redouane59.dz.model.word;

import io.github.Redouane59.dz.model.Possession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PossessiveWord extends GenderedWord {

  private Possession possession;

  public PossessiveWord(GenderedWord genderedWord) {
    super(genderedWord.getGender(), genderedWord.isSingular());
    this.possession = Possession.OTHER;
    this.setTranslations(genderedWord.getTranslations());
  }
}
