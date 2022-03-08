package io.github.Redouane59.dz.model.generator.PVD;

import static io.github.Redouane59.dz.model.generator.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.Optional;

public class PVDSentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    PVDSentence      pvavSentence  = new PVDSentence();
    PersonalProunoun randomPronoun = PersonalProunoun.getRandomPersonalPronoun();
    pvavSentence.setPersonalProunoun(randomPronoun);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), bodyArgs.getTenses(), VerbType.STATE);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PVA");
      return Optional.empty();
    }
    pvavSentence.setVerb(randomVerb.get());
    pvavSentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));

    Adverb randomAdverb = WordPicker.pickRandomAdverb(bodyArgs.getAdverbsFromIds());

    pvavSentence.setAdverb(randomAdverb);
    pvavSentence.addFrTranslation(pvavSentence.buildSentenceValue(Lang.FR));
    pvavSentence.addDzTranslation(pvavSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(pvavSentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return (bodyArgs.getVerbsFromIds().stream().anyMatch(v -> v.getVerbType() == VerbType.STATE));
  }

}
