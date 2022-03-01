package io.github.Redouane59.dz.model.generator.PV;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;

public class PVSentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(BodyArgs bodyArgs) {
    Tense      randomTense      = WordPicker.getRandomTense(bodyArgs.getTenses());
    PVSentence randomPVSentence = new PVSentence();
    randomPVSentence.setTense(randomTense);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PV");
      return Optional.empty();
    }
    randomPVSentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun(randomVerb.get()));
    randomPVSentence.setVerb(randomVerb.get());
    randomPVSentence.addFrTranslation(randomPVSentence.buildSentenceValue(Lang.FR));
    randomPVSentence.addDzTranslation(randomPVSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(randomPVSentence);
  }

}
