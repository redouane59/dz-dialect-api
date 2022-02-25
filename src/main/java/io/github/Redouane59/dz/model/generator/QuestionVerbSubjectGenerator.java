package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.model.InterrogativePronoun;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.sentence.Sentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.List;
import java.util.Optional;

public class QuestionVerbSubjectGenerator extends AbstractSentenceGenerator {

  @Override
  public Optional<Sentence> generateSentence() {
    Sentence             sentence             = new Sentence();
    InterrogativePronoun interrogativePronoun = InterrogativePronoun.getRandomInterrogativeProunoun();
    sentence.setQuestionIds(List.of(interrogativePronoun.getTranslationValue(Lang.FR)));
    Optional<Verb> verb = getRandomVerb(interrogativePronoun);
    if (verb.isEmpty()) {
      sentence.addFrTranslation(interrogativePronoun.getTranslationValue(Lang.FR) + " ?");
      sentence.addDzTranslation(interrogativePronoun.getTranslationValue(Lang.DZ) + " ?");
    } else {
      Conjugator  randomConjugator  = verb.get().getRandomConjugator(getBodyArgs().getTenses()).get();
      Conjugation randomConjugation = verb.get().getRandomConjugation(randomConjugator).get();
      sentence.addFrTranslation(interrogativePronoun.getTranslationValue(Lang.FR) + " "
                                + randomConjugation.getTranslationValue(Lang.FR) + "-"
                                + randomConjugation.getPersonalPronoun(Lang.FR, true)
                                + " ?");
      // @todo determine if pronoun or verb
      if (interrogativePronoun == InterrogativePronoun.WHO && randomConjugator.getTense() == Tense.PRESENT) {
        sentence.addDzTranslation(interrogativePronoun.getTranslationValue(Lang.DZ)
                                  + " "
                                  + randomConjugation.getPersonalPronoun(Lang.DZ, true)
                                  + " ?");
      } else {
        sentence.addDzTranslation(interrogativePronoun.getTranslationValue(Lang.DZ) + " " + randomConjugation.getDzTranslation() + " ?");
      }
    }
    return Optional.of(sentence);

  }


  public Optional<Verb> getRandomVerb(InterrogativePronoun interrogativePronoun) {
    return getBodyArgs().getVerbsFromIds().stream()
                        .filter(o -> o.getPossibleQuestions().contains(interrogativePronoun))
                        .findAny();
  }

}
