package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Conjugation extends PossessiveWord {


  public String getPersonalPronoun(Lang lang, boolean isQuestion) {
    if (isSingular()) {
      if (getPossession() == io.github.Redouane59.dz.model.Possession.I) {
        if (Config.VOWELS.contains(getTranslationValue(Lang.FR).charAt(0)) && !isQuestion) {
          return PersonalProunoun.I2.getTranslationValue(lang);
        }
        return PersonalProunoun.I.getTranslationValue(lang);
      } else if (getPossession() == Possession.YOU) {
        return PersonalProunoun.YOU_M.getTranslationValue(lang);
      } else if (getPossession() == Possession.OTHER) {
        if (getGender() == Gender.F) {
          return PersonalProunoun.SHE.getTranslationValue(lang);
        } else {
          return PersonalProunoun.HE.getTranslationValue(lang);
        }
      }
    } else {
      if (getPossession() == io.github.Redouane59.dz.model.Possession.I) {
        return PersonalProunoun.WE.getTranslationValue(lang);
      } else if (getPossession() == io.github.Redouane59.dz.model.Possession.YOU) {
        return PersonalProunoun.YOU_P.getTranslationValue(lang);
      } else if (getPossession() == io.github.Redouane59.dz.model.Possession.OTHER) {
        return PersonalProunoun.THEY_M.getTranslationValue(lang);
      }
    }
    return PersonalProunoun.UNDEFINED.getTranslationValue(lang);
  }

}
