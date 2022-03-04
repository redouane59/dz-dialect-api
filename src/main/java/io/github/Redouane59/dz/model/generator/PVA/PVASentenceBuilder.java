package io.github.Redouane59.dz.model.generator.PVA;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.List;
import java.util.Optional;

public class PVASentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(BodyArgs bodyArgs) {
    PVASentence      pvaSentence   = new PVASentence();
    PersonalProunoun randomPronoun = PersonalProunoun.getRandomPersonalPronoun();
    pvaSentence.setPersonalProunoun(randomPronoun);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), bodyArgs.getTenses(), VerbType.STATE);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PVA");
      return Optional.empty();
    }
    pvaSentence.setVerb(randomVerb.get());
    pvaSentence.setTense(randomVerb.get().getRandomConjugator(bodyArgs.getTenses()).get().getTense());
    Optional<Adjective> randomAdjective;
    if (randomPronoun.getPossession() == Possession.OTHER) {
      randomAdjective = WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds());
    } else {
      randomAdjective = WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds(), List.of(NounType.PERSON));
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
  public boolean isCompatible(final BodyArgs bodyArgs) {
    return (bodyArgs.getVerbsFromIds().stream().anyMatch(v -> v.getVerbType() == VerbType.STATE));
  }

}
