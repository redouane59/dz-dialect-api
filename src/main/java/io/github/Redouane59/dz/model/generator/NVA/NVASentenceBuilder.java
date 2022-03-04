package io.github.Redouane59.dz.model.generator.NVA;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.List;
import java.util.Optional;

public class NVASentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(BodyArgs bodyArgs) {
    NVASentence    nvaSentence = new NVASentence();
    Optional<Noun> randomNoun  = WordPicker.pickRandomNoun(bodyArgs.getNounsFromIds(), List.of(NounType.PLACE, NounType.PERSON, NounType.OBJECT));
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
    nvaSentence.setTense(randomVerb.get().getRandomConjugator(bodyArgs.getTenses()).get().getTense());
    Optional<Adjective>
        randomAdjective =
        WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds(), randomNoun.get().getNounTypes());
    if (randomAdjective.isEmpty()) {
      return Optional.empty();
    }
    nvaSentence.setAdjective(randomAdjective.get());
    nvaSentence.addFrTranslation(nvaSentence.buildSentenceValue(Lang.FR));
    nvaSentence.addDzTranslation(nvaSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(nvaSentence);
  }

  @Override
  public boolean isCompatible(final BodyArgs bodyArgs) {
    Optional<List<NounType>> adjectiveCompatibleNouns = WordPicker.getCompatibleNounsFromAdjectives(bodyArgs.getAdjectivesFromIds());
    return adjectiveCompatibleNouns.isPresent() && !adjectiveCompatibleNouns.get().isEmpty();
  }


}
