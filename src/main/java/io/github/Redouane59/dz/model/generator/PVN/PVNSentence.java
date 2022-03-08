package io.github.Redouane59.dz.model.generator.PVN;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.PV.PVSentence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PVNSentence extends PVSentence {

  @Override
  public String buildSentenceValue(final Lang lang) {
    String result    = super.buildSentenceValue(lang);
    String nounValue = getNoun().getTranslationBySingular(true, lang).get().getValue();
    result += " ";
    switch (getVerb().getVerbType()) {
      case STATE:
        result += getNoun().getStatePreposition(lang).get().getValue();
        break;
      case DEPLACEMENT:
        result += getNoun().getDeplacementProposition(lang).get().getValue();
        break;
      case ACTION:
        result += getNoun().getValues().get(0).getGender().getArticleTranslationValue(lang, nounValue);
        break;
      case POSSESSION:
        result += getNoun().getValues().get(0).getGender().getUndefinedArticleTranslationValue(lang);
        break;
    }

    result += " ";
    result += nounValue;

    return cleanResponse(result);
  }

}
