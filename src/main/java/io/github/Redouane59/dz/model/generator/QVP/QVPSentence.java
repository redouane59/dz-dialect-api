package io.github.Redouane59.dz.model.generator.QVP;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Question;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QVPSentence extends AbstractSentence {

  @JsonIgnore
  private PersonalProunoun personalProunoun;
  @JsonIgnore
  private Verb             verb;
  @JsonIgnore
  private Tense            tense;
  @JsonIgnore
  private Question         question;

  @Override
  public String buildSentenceValue(final Lang lang) {
    String               ppValue    = personalProunoun.getTranslationValue(lang);
    Optional<Conjugator> conjugator = verb.getConjugationByTense(tense);
    if (conjugator.isEmpty()) {
      System.err.println("empty conjugator");
      return "";
    }
    Optional<Conjugation> conjugation = conjugator.get().getConjugationByCriteria(personalProunoun.getGender(),
                                                                                  personalProunoun.isSingular(),
                                                                                  personalProunoun.getPossession());
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
    return cleanResponse(result);
  }

}
