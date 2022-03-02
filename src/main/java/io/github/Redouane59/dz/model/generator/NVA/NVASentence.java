package io.github.Redouane59.dz.model.generator.NVA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.GenderedWord;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NVASentence extends AbstractSentence {

  @JsonIgnore
  private Noun      noun;
  @JsonIgnore
  private Verb      verb;
  @JsonIgnore
  private Adjective adjective;
  @JsonIgnore
  private Tense     tense;

  @Override
  public String buildSentenceValue(final Lang lang) {
    GenderedWord         nounWord   = noun.getWordBySingular(true);//@todo random singular
    String               nounValue  = nounWord.getTranslationValue(lang);
    Optional<Conjugator> conjugator = verb.getConjugationByTense(tense);
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
    if (Config.DISPLAY_STATE_VERB.contains(lang) || tense != Tense.PRESENT) {
      result += verbValue + " ";
    }
    result += adjective.getTranslationByGender(nounWord.getGender(), nounWord.isSingular(), lang).getValue();

    return cleanResponse(result);
  }

}
