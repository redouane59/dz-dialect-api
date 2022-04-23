package io.github.Redouane59.dz.model.sentence.PV;


import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.RootLang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Tense;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*
  Pronoun + Verb
 */
public class PVSentence extends AbstractSentence {


  @Override
  public Translation buildSentenceValue(final Lang lang) {
    Optional<Conjugator> conjugator = getVerb().getConjugationByTense(getTense());
    if (conjugator.isEmpty()) {
      throw new IllegalArgumentException("empty conjugator for verb " + getVerb().getId() + " and tense " + getTense());
    }

    Optional<Conjugation> conjugation = conjugator.get().getConjugationByCriteria(getPersonalProunoun().getGender(),
                                                                                  getPersonalProunoun().isSingular(),
                                                                                  getPersonalProunoun().getPossession());
    if (conjugation.isEmpty()) {
      System.err.println("empty conjugation for verb "
                         + getVerb().getId()
                         + ", gender "
                         + getPersonalProunoun().getGender()
                         + ", singular "
                         + getPersonalProunoun().isSingular()
                         + " and posession "
                         + getPersonalProunoun().getPossession());
      return new Translation(lang, ""); // @todo to remove
    }

    if (lang == Lang.FR) {
      return buildFrSentenceValue(conjugation.get());
    } else if (lang.getRootLang() == RootLang.AR) {
      return buildArSentenceValue(conjugation.get(), lang);
    } else {
      throw new IllegalArgumentException("no lang found");
    }
  }

  public Translation buildFrSentenceValue(Conjugation conjugation) {
    String sentence = "";
    if (getTense() != Tense.IMPERATIVE) {
      sentence = getPersonalProunoun().getTranslationValue(Lang.FR) + " ";
    }
    sentence += conjugation.getTranslationValue(Lang.FR);
    return new Translation(Lang.FR, sentence);
  }

  public Translation buildArSentenceValue(Conjugation conjugation, Lang lang) {
    return new Translation(lang, conjugation.getTranslationValue(lang), conjugation.getDzTranslationAr());
  }

}
