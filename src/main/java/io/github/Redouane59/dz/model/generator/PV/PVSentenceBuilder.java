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
    Tense      randomTense = WordPicker.getRandomTense(bodyArgs.getTenses());
    PVSentence pvSentence  = new PVSentence();
    pvSentence.setTense(randomTense);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PV");
      return Optional.empty();
    }
    pvSentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun(randomVerb.get()));
    pvSentence.setVerb(randomVerb.get());
    pvSentence.setTense(randomVerb.get().getRandomConjugator(bodyArgs.getTenses()).get().getTense());
    pvSentence.addFrTranslation(pvSentence.buildSentenceValue(Lang.FR));
    pvSentence.addDzTranslation(pvSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(pvSentence);
  }

  @Override
  public boolean isCompatible(final BodyArgs bodyArgs) {
    return true;
  }

}
