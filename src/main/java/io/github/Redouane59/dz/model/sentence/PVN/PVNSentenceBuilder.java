package io.github.Redouane59.dz.model.sentence.PVN;

import static io.github.Redouane59.dz.model.sentence.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.sentence.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.WordPicker;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;

public class PVNSentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    PVNSentence randomPVNSentence = new PVNSentence();
    Optional<Verb>
        randomVerb =
        WordPicker.pickRandomVerb(WordPicker.getCompatibleVerbs(bodyArgs.getVerbsFromIds(), bodyArgs.getNounsFromIds()), bodyArgs.getTenses());
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PVN (2)");
      return Optional.empty();
    }
    randomPVNSentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));
    randomPVNSentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun());
    randomPVNSentence.setVerb(randomVerb.get());
    Optional<Noun> randomNoun = WordPicker.pickRandomNoun(bodyArgs.getNounsFromIds(), randomVerb.get().getPossibleComplements());
    if (randomNoun.isEmpty()) {
      System.out.println("No randomVerb found in PVN");
      return Optional.empty();
    }
    randomPVNSentence.setNoun(randomNoun.get());
    randomPVNSentence.addFrTranslation(randomPVNSentence.buildSentenceValue(Lang.FR));
    randomPVNSentence.addDzTranslation(randomPVNSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(randomPVNSentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return !WordPicker.getCompatibleVerbs(bodyArgs.getVerbsFromIds(), bodyArgs.getNounsFromIds()).isEmpty();
  }

}
