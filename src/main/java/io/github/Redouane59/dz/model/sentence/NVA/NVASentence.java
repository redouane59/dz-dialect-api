package io.github.Redouane59.dz.model.sentence.NVA;

import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Article;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.word.GenderedWord;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

/*
  Noun + verb + adjectif
 */
public class NVASentence extends AbstractSentence {

  @Override
  public Translation buildSentenceValue(final Lang lang) {
    boolean              isSingular = true; //new Random().nextInt() % 2 == 0;
    GenderedWord         nounWord   = getNoun().getWordBySingular(isSingular);
    String               nounValue  = nounWord.getTranslationValue(lang);
    Optional<Conjugator> conjugator = getVerb().getConjugationByTense(getTense());
    if (conjugator.isEmpty()) {
      System.err.println("empty conjugator");
      return new Translation(lang, "");
    }
    Gender nounGender = nounWord.getGender(lang);
    Optional<Conjugation> conjugation = conjugator.get().getConjugationByCriteria(nounGender,
                                                                                  nounWord.isSingular(),
                                                                                  Possession.OTHER);
    String verbValue = "";
    if (conjugation.isEmpty()) {
      System.err.println("empty conjugation");
    } else {
      verbValue = conjugation.get().getTranslationValue(lang);
    }

    String result = Article.getArticle(nounGender, nounWord.isSingular(), true)
                           .get().getTranslationValue(lang, nounValue) + " ";
    result += nounValue + " ";
    if (Config.DISPLAY_STATE_VERB.contains(lang) || getTense() != Tense.PRESENT) {
      result += verbValue + " ";
    }
    result += getAdjective().getTranslationByGender(nounGender, nounWord.isSingular(), lang).getValue();

    return new Translation(lang, result);
  }

}
