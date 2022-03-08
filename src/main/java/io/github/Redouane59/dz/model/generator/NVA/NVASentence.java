package io.github.Redouane59.dz.model.generator.NVA;

import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.word.GenderedWord;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NVASentence extends AbstractSentence {

  @Override
  public String buildSentenceValue(final Lang lang) {
    GenderedWord         nounWord   = getNoun().getWordBySingular(true);//@todo random singular
    String               nounValue  = nounWord.getTranslationValue(lang);
    Optional<Conjugator> conjugator = getVerb().getConjugationByTense(getTense());
    if (conjugator.isEmpty()) {
      System.err.println("empty conjugator");
      return "";
    }
    Optional<Conjugation> conjugation = conjugator.get().getConjugationByCriteria(nounWord.getGender(),
                                                                                  nounWord.isSingular(),
                                                                                  Possession.OTHER);
    String verbValue = "";
    if (conjugation.isEmpty()) {
      System.err.println("empty conjugation");
    } else {
      verbValue = conjugation.get().getTranslationValue(lang);
    }

    String result = nounWord.getGender().getArticleTranslationValue(lang, nounValue) + " ";
    result += nounValue + " ";
    if (Config.DISPLAY_STATE_VERB.contains(lang) || getTense() != Tense.PRESENT) {
      result += verbValue + " ";
    }
    result += getAdjective().getTranslationByGender(nounWord.getGender(), nounWord.isSingular(), lang).getValue();

    return cleanResponse(result);
  }

}
