package io.github.Redouane59.dz.model.sentence.NVA;

import static io.github.Redouane59.dz.model.sentence.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.sentence.ISentenceBuilder;
import io.github.Redouane59.dz.model.sentence.WordPicker;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class NVASentenceBuilder implements ISentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    NVASentence nvaSentence = new NVASentence();
    Optional<Noun>
        randomNoun =
        WordPicker.pickRandomNoun(bodyArgs.getNounsFromIds(), Set.of(NounType.PLACE, NounType.PERSON, NounType.OBJECT, NounType.FOOD));
    if (randomNoun.isEmpty()) {
      return Optional.empty();
    }
    nvaSentence.setNoun(randomNoun.get());
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), bodyArgs.getTenses(), VerbType.STATE);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in NVA");
      return Optional.empty();
    }
    nvaSentence.setVerb(randomVerb.get());
    nvaSentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));
    Optional<Adjective>
        randomAdjective =
        WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds(), randomNoun.get().getNounTypes());
    if (randomAdjective.isEmpty()) {
      return Optional.empty();
    }
    nvaSentence.setAdjective(randomAdjective.get());
    nvaSentence.addFrTranslation(nvaSentence.buildSentenceValue(Lang.FR).getValue());
    Translation dzTranslation = nvaSentence.buildSentenceValue(Lang.DZ);
    nvaSentence.addDzTranslation(dzTranslation.getValue(), dzTranslation.getArValue());
    return Optional.of(nvaSentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    Optional<Set<NounType>> adjectiveCompatibleNouns = WordPicker.getCompatibleNounsFromAdjectives(bodyArgs.getAdjectivesFromIds());
    if (adjectiveCompatibleNouns.isEmpty() || adjectiveCompatibleNouns.get().isEmpty()) {
      return false;
    }
    List<Verb> stateVerbs = bodyArgs.getVerbsFromIds().stream().filter(v -> v.getVerbType() == VerbType.STATE).collect(Collectors.toList());
    return !stateVerbs.isEmpty();
  }


}
