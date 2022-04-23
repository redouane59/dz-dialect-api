package io.github.Redouane59.dz.model.sentence.PV;

import static io.github.Redouane59.dz.model.sentence.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.sentence.ISentenceBuilder;
import io.github.Redouane59.dz.model.sentence.WordPicker;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;

public class PVSentenceBuilder implements ISentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    PVSentence     pvSentence = new PVSentence();
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), bodyArgs.getTenses());
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PV");
      return Optional.empty();
    }
    pvSentence.setVerb(randomVerb.get());
    pvSentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun(randomVerb.get()));
    pvSentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));
    Translation frTransation = pvSentence.buildSentenceValue(Lang.FR);
    pvSentence.addTranslation(Lang.FR, frTransation.getValue());
    Translation dzTranslation = pvSentence.buildSentenceValue(Lang.DZ);
    pvSentence.addTranslation(Lang.DZ, dzTranslation.getValue(), dzTranslation.getArValue());
    return Optional.of(pvSentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return bodyArgs.getVerbsFromIds().stream().map(Verb::getConjugators)
                   .map(c -> c.stream()
                              .filter(v -> bodyArgs.getTenses().contains(v.getTense()))
                              .map(Conjugator::getConjugations))
                   .findAny().isPresent();
  }

}
