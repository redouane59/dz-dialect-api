package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class Conjugation extends PossessiveWord {

  @JsonIgnore
  public PersonalProunoun getPersonalPronoun() {
    if (isSingular()) {
      if (getPossession() == io.github.Redouane59.dz.model.Possession.I) {
        return PersonalProunoun.I;
      } else if (getPossession() == Possession.YOU) {
        if (getGender() == Gender.F) {
          return PersonalProunoun.YOU_F;
        } else {
          return PersonalProunoun.YOU_M;
        }
      } else if (getPossession() == Possession.OTHER) {
        if (getGender() == Gender.F) {
          return PersonalProunoun.SHE;
        } else {
          return PersonalProunoun.HE;
        }
      }
    } else {
      if (getPossession() == io.github.Redouane59.dz.model.Possession.I) {
        return PersonalProunoun.WE;
      } else if (getPossession() == io.github.Redouane59.dz.model.Possession.YOU) {
        return PersonalProunoun.YOU_P;
      } else if (getPossession() == io.github.Redouane59.dz.model.Possession.OTHER) {
        if (getGender() == Gender.F) {
          return PersonalProunoun.THEY_F;
        } else {
          return PersonalProunoun.THEY_M;
        }
      }
    }
    return PersonalProunoun.UNDEFINED;
  }

}
