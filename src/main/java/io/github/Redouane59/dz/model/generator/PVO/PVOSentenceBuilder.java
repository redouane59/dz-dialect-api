package io.github.Redouane59.dz.model.generator.PVO;

import static io.github.Redouane59.dz.model.generator.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;

public class PVOSentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    PVOSentence    randomPVNSentence = new PVOSentence();
    Optional<Verb> randomVerb        = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), true);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PVN (2)");
      return Optional.empty();
    }
    randomPVNSentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));
    PersonalProunoun personalProunoun = PersonalProunoun.getRandomPersonalPronoun();
    randomPVNSentence.setPersonalProunoun(personalProunoun);
    randomPVNSentence.setSuffixPronoun(personalProunoun.getRandomDifferentPersonalPronoun());
    randomPVNSentence.setVerb(randomVerb.get());
    randomPVNSentence.addFrTranslation(randomPVNSentence.buildSentenceValue(Lang.FR));
    randomPVNSentence.addDzTranslation(randomPVNSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(randomPVNSentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return bodyArgs.getVerbsFromIds().stream().anyMatch(o -> o.getReflexiveSuffixDz() != null);
  }

}
