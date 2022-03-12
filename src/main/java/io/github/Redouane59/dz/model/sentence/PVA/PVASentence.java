package io.github.Redouane59.dz.model.sentence.PVA;

import io.github.Redouane59.dz.model.Lang;
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
  public String buildSentenceValue(final Lang lang) {
    String pv = super.buildSentenceValue(lang);
    return cleanResponse(pv
                         + " "
                         + getAdjective().getTranslationByGender(getPersonalProunoun().getGender(), getPersonalProunoun().isSingular(), lang)
                                         .getValue());
  }

}
