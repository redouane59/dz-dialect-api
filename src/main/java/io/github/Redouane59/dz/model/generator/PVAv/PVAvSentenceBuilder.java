package io.github.Redouane59.dz.model.generator.PVAv;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.Optional;

public class PVAvSentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(BodyArgs bodyArgs) {
    PVAvSentence     pvavSentence  = new PVAvSentence();
    PersonalProunoun randomPronoun = PersonalProunoun.getRandomPersonalPronoun();
    pvavSentence.setPersonalProunoun(randomPronoun);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), bodyArgs.getTenses(), VerbType.STATE);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PVA");
      return Optional.empty();
    }
    pvavSentence.setVerb(randomVerb.get());
    pvavSentence.setTense(randomVerb.get().getRandomConjugator(bodyArgs.getTenses()).get().getTense());

    Adverb randomAdverb = WordPicker.pickRandomAdverb(bodyArgs.getAdverbsFromIds());

    pvavSentence.setAdverb(randomAdverb);
    pvavSentence.addFrTranslation(pvavSentence.buildSentenceValue(Lang.FR));
    pvavSentence.addDzTranslation(pvavSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(pvavSentence);
  }

  @Override
  public boolean isCompatible(final BodyArgs bodyArgs) {
    return (bodyArgs.getVerbsFromIds().stream().anyMatch(v -> v.getVerbType() == VerbType.STATE));
  }

}
