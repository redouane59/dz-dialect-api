package io.github.Redouane59.dz.model.generator.PVN;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.PV.PVSentence;
import io.github.Redouane59.dz.model.noun.Noun;
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
    switch (getVerb().getVerbType()) {
      case STATE:
        result += noun.getStatePreposition(lang).get().getValue();
        break;
      case DEPLACEMENT:
        result += noun.getDeplacementProposition(lang).get().getValue();
        break;
      case ACTION:
        result += noun.getValues().get(0).getGender().getArticleTranslationValue(lang, nounValue);
        break;
    }

    result += " ";
    result += nounValue;

    return cleanResponse(result); // @todo to set above?
  }

}
