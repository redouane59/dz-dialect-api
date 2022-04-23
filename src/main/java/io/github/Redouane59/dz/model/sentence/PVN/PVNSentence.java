package io.github.Redouane59.dz.model.sentence.PVN;

import io.github.Redouane59.dz.model.Article;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.sentence.PV.PVSentence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*
  Pronoun + verb + noun
 */
public class PVNSentence extends PVSentence {

  @Override
  public Translation buildSentenceValue(final Lang lang) {
    String result    = super.buildSentenceValue(lang).getValue();
    String nounValue = getNoun().getTranslationBySingular(true, lang).get().getValue();
    result += " ";
    if (getVerb().getVerbType() == null) {
      System.err.println("verb type null");
      return new Translation(lang, "");
    }
    switch (getVerb().getVerbType()) {
      case STATE:
        result += getNoun().getStatePreposition(lang).get().getValue();
        break;
      case DEPLACEMENT:
        result += getNoun().getDeplacementProposition(lang).get().getValue();
        break;
      case ACTION:
        result += Article.getArticle(getNoun().getValues().get(0).getGender(lang), true, true)
                         .get().getTranslationValue(lang, nounValue);
        break;
      case POSSESSION:
        result += Article.getArticle(getNoun().getValues().get(0).getGender(lang), true, false)
                         .get().getTranslationValue(lang, nounValue);
        break;
    }

    result += " ";
    result += nounValue;

    return new Translation(lang, result);
  }

}
