package io.github.Redouane59.dz.model.sentence.PVA;

import static io.github.Redouane59.dz.model.sentence.WordPicker.getRandomTense;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.sentence.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.Optional;
import java.util.Set;

public class PVASentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    PVASentence      pvaSentence   = new PVASentence();
    PersonalProunoun randomPronoun = PersonalProunoun.getRandomPersonalPronoun();
    pvaSentence.setPersonalProunoun(randomPronoun);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), bodyArgs.getTenses(), VerbType.STATE);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PVA");
      return Optional.empty();
    }
    pvaSentence.setVerb(randomVerb.get());
    pvaSentence.setTense(getRandomTense(randomVerb.get(), bodyArgs.getTenses()));
    Optional<Adjective> randomAdjective;
    if (randomPronoun.getPossession() == Possession.OTHER) {
      randomAdjective = WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds());
    } else {
      randomAdjective = WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds(), Set.of(NounType.PERSON));
    }
    if (randomAdjective.isEmpty()) {
      return Optional.empty();
    }
    pvaSentence.setAdjective(randomAdjective.get());
    pvaSentence.addFrTranslation(pvaSentence.buildSentenceValue(Lang.FR));
    pvaSentence.addDzTranslation(pvaSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(pvaSentence);
  }

  @Override
  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return (bodyArgs.getVerbsFromIds().stream().anyMatch(v -> v.getVerbType() == VerbType.STATE));
  }

}
