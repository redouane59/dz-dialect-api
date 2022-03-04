package io.github.Redouane59.dz.model.generator.PVAv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.generator.PV.PVSentence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PVAvSentence extends PVSentence {

  @JsonIgnore
  private Adverb adverb;

  @Override
  public String buildSentenceValue(final Lang lang) {
    String pv = super.buildSentenceValue(lang);
    return cleanResponse(pv + " " + adverb.getTranslationValue(lang));
  }

}
