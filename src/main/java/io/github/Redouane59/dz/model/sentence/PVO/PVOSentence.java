package io.github.Redouane59.dz.model.sentence.PVO;

import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.RootLang;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*
  Pronoun + verb + suffix
 */
public class PVOSentence extends AbstractSentence {


  public String buildSentenceValue(final Lang lang) {
    String               ppValue    = getPersonalProunoun().getTranslationValue(lang);
    Optional<Conjugator> conjugator = getVerb().getConjugationByTense(getTense());
    if (conjugator.isEmpty()) {
      System.err.println("empty conjugator");
      return "";
    }
    Optional<Conjugation> conjugation = conjugator.get().getConjugationByCriteria(getPersonalProunoun().getGender(),
                                                                                  getPersonalProunoun().isSingular(),
                                                                                  getPersonalProunoun().getPossession());

    String verbValue = "";
    if (conjugation.isEmpty()) {
      System.err.println("empty conjugation");
    } else {
      verbValue = conjugation.get().getTranslationValue(lang);
    }
    String result = "";
    if (Config.DISPLAY_PROUNOUNS.contains(lang)) {
      result = ppValue + " ";
    }
    if (lang == Lang.FR && getVerb().getReflexiveSuffixFr() != null) {
      result += getVerb().getReflexiveSuffixFr().getSuffixValue(getSuffixPronoun(), verbValue) + " ";
    }
    result += verbValue;
    if (lang.getRootLang() == RootLang.AR && getVerb().getReflexiveSuffixDz() != null) {
      result += getVerb().getReflexiveSuffixDz().getSuffixValue(getSuffixPronoun(), verbValue);
    }
    return cleanResponse(result);
  }

}