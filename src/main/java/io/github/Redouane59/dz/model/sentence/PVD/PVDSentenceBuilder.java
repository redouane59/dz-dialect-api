package io.github.Redouane59.dz.model.sentence.PVD;

import static io.github.Redouane59.dz.model.sentence.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.sentence.ISentenceBuilder;
import io.github.Redouane59.dz.model.sentence.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.Optional;

public class PVDSentenceBuilder implements ISentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    PVDSentence      pvavSentence  = new PVDSentence();
    PersonalProunoun randomPronoun = PersonalProunoun.getRandomPersonalPronoun();
    pvavSentence.setPersonalProunoun(randomPronoun);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerbAdverbCompatible(bodyArgs.getVerbsFromIds(), bodyArgs.getTenses());
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PVA");
      return Optional.empty();
    }
    pvavSentence.setVerb(randomVerb.get());
    pvavSentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));

    Adverb randomAdverb = WordPicker.pickRandomAdverb(bodyArgs.getAdverbsFromIds());

    pvavSentence.setAdverb(randomAdverb);
    pvavSentence.addFrTranslation(pvavSentence.buildSentenceValue(Lang.FR).getValue());
    Translation dzTranslation = pvavSentence.buildSentenceValue(Lang.DZ);
    pvavSentence.addDzTranslation(dzTranslation.getValue(), dzTranslation.getArValue());
    return Optional.of(pvavSentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return (bodyArgs.getVerbsFromIds().stream().anyMatch(v -> v.getVerbType() == VerbType.STATE)
            || bodyArgs.getVerbsFromIds().stream().anyMatch(v -> v.getPossibleComplements().contains(NounType.ADVERB)));
  }

}
