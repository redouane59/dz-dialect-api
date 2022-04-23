package io.github.Redouane59.dz.model.sentence.PVSO;

import static io.github.Redouane59.dz.model.sentence.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.sentence.ISentenceBuilder;
import io.github.Redouane59.dz.model.sentence.WordPicker;
import io.github.Redouane59.dz.model.verb.GenericSuffixes.Suffix;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;

public class PVSOSentenceBuilder implements ISentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    PVSOSentence   randomPVONSentence = new PVSOSentence();
    Optional<Verb> randomVerb         = WordPicker.pickRandomIndirectOrDirectVerb(bodyArgs.getVerbsFromIds());
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PVN (2)");
      return Optional.empty();
    }
    randomPVONSentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));
    PersonalProunoun personalProunoun = PersonalProunoun.getRandomPersonalPronoun();
    randomPVONSentence.setPersonalProunoun(personalProunoun);
    boolean isObject = randomVerb.get().isObjectOnly();
    randomPVONSentence.setSuffix(Suffix.getRandomSuffix(personalProunoun.getPossession(), isObject));
    randomPVONSentence.setVerb(randomVerb.get());
    if (randomVerb.get().isIndirectComplement() && !randomVerb.get().getPossibleComplements().isEmpty()) {
      randomPVONSentence.setNoun(WordPicker.pickRandomNoun(DB.NOUNS, randomVerb.get().getPossibleComplements()).get());
    }
    randomPVONSentence.addFrTranslation(randomPVONSentence.buildSentenceValue(Lang.FR).getValue());
    Translation dzTranslation = randomPVONSentence.buildSentenceValue(Lang.DZ);
    randomPVONSentence.addDzTranslation(dzTranslation.getValue(), dzTranslation.getArValue());
    return Optional.of(randomPVONSentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return bodyArgs.getVerbsFromIds().stream().anyMatch(o -> o.isIndirectComplement() && !o.isDzNoSuffix());
  }

}
