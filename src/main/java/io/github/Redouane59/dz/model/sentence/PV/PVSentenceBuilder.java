package io.github.Redouane59.dz.model.sentence.PV;

import static io.github.Redouane59.dz.model.sentence.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.sentence.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.Optional;

public class PVSentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    PVSentence     pvSentence = new PVSentence();
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), bodyArgs.getTenses());
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PV");
      return Optional.empty();
    }
    pvSentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));
    pvSentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun());
    pvSentence.setVerb(randomVerb.get());
    pvSentence.addFrTranslation(pvSentence.buildSentenceValue(Lang.FR));
    pvSentence.addDzTranslation(pvSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(pvSentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return (bodyArgs.getVerbsFromIds().stream().noneMatch(v -> v.getPossibleComplements() != null))
           && (bodyArgs.getVerbsFromIds().stream().noneMatch(v -> v.getVerbType() != VerbType.STATE));
  }

}
