package io.github.Redouane59.dz.model.generator.QVP;

import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QVPSentence extends AbstractSentence {

  @Override
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
    String result = getQuestion().getTranslationValue(lang) + " ";
    if (Config.DISPLAY_PROUNOUNS.contains(lang)) {
      result += ppValue + " ";
    }
    result += verbValue;
    result += "?";
    return cleanResponse(result); // @todo force it in mother class
  }

}
