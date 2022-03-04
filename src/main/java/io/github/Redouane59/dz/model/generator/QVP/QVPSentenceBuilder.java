package io.github.Redouane59.dz.model.generator.QVP;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Question;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;

public class QVPSentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(BodyArgs bodyArgs) {
    Tense       randomTense = WordPicker.getRandomTense(bodyArgs.getTenses());
    QVPSentence qvp         = new QVPSentence();
    qvp.setTense(randomTense);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in QVP");
      return Optional.empty();
    }
    Question question = WordPicker.pickRandomInterrogativePronoun(randomVerb.get());
    qvp.setQuestion(question);
    qvp.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun(randomVerb.get()));
    qvp.setVerb(randomVerb.get());
    qvp.addFrTranslation(qvp.buildSentenceValue(Lang.FR));
    qvp.addDzTranslation(qvp.buildSentenceValue(Lang.DZ));
    return Optional.of(qvp);
  }

  @Override
  public boolean isCompatible(final BodyArgs bodyArgs) {
    return true;
  }

}
