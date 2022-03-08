package io.github.Redouane59.dz.model.generator.PVD;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.PV.PVSentence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PVDSentence extends PVSentence {


  @Override
  public String buildSentenceValue(final Lang lang) {
    String pv = super.buildSentenceValue(lang);
    return cleanResponse(pv + " " + getAdverb().getTranslationValue(lang));
  }

}
