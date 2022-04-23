package io.github.Redouane59.dz.model.sentence.PVA;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.sentence.PV.PVSentence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*
  Pronoun + Verb + Adjectif
 */
public class PVASentence extends PVSentence {


  @Override
  public Translation buildSentenceValue(final Lang lang) {
    Translation pv = super.buildSentenceValue(lang);
    return new Translation(lang, pv.getValue() + " "
                                 + getAdjective().getTranslationByGender(getPersonalProunoun().getGender(), getPersonalProunoun().isSingular(), lang)
                                                 .getValue());
  }

}
