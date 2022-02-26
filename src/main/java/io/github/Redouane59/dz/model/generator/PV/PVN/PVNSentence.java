package io.github.Redouane59.dz.model.generator.PV.PVN;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.generator.PV.PVSentence;
import io.github.Redouane59.dz.model.verb.VerbType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PVNSentence extends PVSentence {

  @JsonIgnore
  private Noun noun;

  @Override
  public String buildSentenceValue(final Lang lang) {
    String result    = super.buildSentenceValue(lang);
    String nounValue = noun.getTranslationBySingular(true, lang).get().getValue();
    result += " ";
    if (getVerb().getVerbType() == VerbType.STATE || getVerb().getVerbType() == VerbType.DEPLACEMENT) {
      result += getVerb().getVerbType().getPlacePreposition(lang, nounValue);
      result += " ";
    }

    if (lang == Lang.FR || (getVerb().getVerbType() != VerbType.STATE
                            && getVerb().getVerbType() != VerbType.DEPLACEMENT)) {
      result += noun.getWordBySingular(true).getGender().getArticleTranslationValue(lang, nounValue);
      result += " ";
    }
    result += nounValue;

    return result;
  }

}
