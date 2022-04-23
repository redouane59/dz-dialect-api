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
        if (getGender() == Gender.F) {
          return PersonalProunoun.I_F;
        } else {
          return PersonalProunoun.I_M;
        }
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
        return PersonalProunoun.WE_M;
      } else if (getPossession() == io.github.Redouane59.dz.model.Possession.YOU) {
        if (getGender() == Gender.F) {
          return PersonalProunoun.YOU_PF;
        } else {
          return PersonalProunoun.YOU_PM;
        }
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
