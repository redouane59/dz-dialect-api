package io.github.Redouane59.dz.model.generator.PVN;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;

public class PVNSentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(BodyArgs bodyArgs) {
    Tense       randomTense       = WordPicker.getRandomTense(bodyArgs.getTenses());
    PVNSentence randomPVNSentence = new PVNSentence();
    randomPVNSentence.setTense(randomTense);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense);
    if (randomVerb.isEmpty()) {
      randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds()); // without tense
      if (randomVerb.isEmpty()) {
        System.out.println("No randomVerb found in PVN");
        return Optional.empty();
      }
      randomPVNSentence.setTense(randomVerb.get().getConjugators().get(0).getTense()); // @todo dirty
    }
    randomPVNSentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun(randomVerb.get()));
    randomPVNSentence.setVerb(randomVerb.get());
    Optional<Noun> randomNoun = WordPicker.pickRandomNoun(bodyArgs.getNounsFromIds(), WordType.PLACE);
    if (randomNoun.isEmpty()) {
      System.out.println("No randomVerb found in PVN");
      return Optional.empty();
    }
    randomPVNSentence.setNoun(randomNoun.get());
    randomPVNSentence.addFrTranslation(randomPVNSentence.buildSentenceValue(Lang.FR));
    randomPVNSentence.addDzTranslation(randomPVNSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(randomPVNSentence);
  }

}
