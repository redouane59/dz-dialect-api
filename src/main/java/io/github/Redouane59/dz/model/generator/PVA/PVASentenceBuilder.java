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
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.List;
import java.util.Optional;

public class PVASentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(BodyArgs bodyArgs) {
    Tense            randomTense   = WordPicker.getRandomTense(bodyArgs.getTenses());
    PVASentence      PVASentence   = new PVASentence();
    PersonalProunoun randomPronoun = PersonalProunoun.getRandomPersonalPronoun();
    PVASentence.setPersonalProunoun(randomPronoun);
    PVASentence.setTense(randomTense);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense, VerbType.STATE);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in PVA");
      return Optional.empty();
    }
    PVASentence.setVerb(randomVerb.get());
    Optional<Adjective> randomAdjective;
    if (randomPronoun.getPossession() == Possession.OTHER) {
      randomAdjective = WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds());
    } else {
      randomAdjective = WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds(), List.of(NounType.PERSON));
    }
    if (randomAdjective.isEmpty()) {
      return Optional.empty();
    }
    PVASentence.setAdjective(randomAdjective.get());
    PVASentence.addFrTranslation(PVASentence.buildSentenceValue(Lang.FR));
    PVASentence.addDzTranslation(PVASentence.buildSentenceValue(Lang.DZ));
    return Optional.of(PVASentence);
  }

}
