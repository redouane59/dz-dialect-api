package io.github.Redouane59.dz.model.sentence.QVP;

import static io.github.Redouane59.dz.model.sentence.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.question.Question;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.sentence.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;

public class QVPSentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    QVPSentence    sentence   = new QVPSentence();
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), bodyArgs.getTenses());
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in QVP");
      return Optional.empty();
    }
    sentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));
    Question question = WordPicker.pickRandomInterrogativePronoun(randomVerb.get());
    sentence.setQuestion(question);
    sentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun());
    sentence.setVerb(randomVerb.get());
    sentence.addFrTranslation(sentence.buildSentenceValue(Lang.FR));
    sentence.addDzTranslation(sentence.buildSentenceValue(Lang.DZ));
    return Optional.of(sentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return (bodyArgs.getVerbsFromIds().stream().anyMatch(v -> !v.getPossibleComplements().isEmpty()));
  }

}
