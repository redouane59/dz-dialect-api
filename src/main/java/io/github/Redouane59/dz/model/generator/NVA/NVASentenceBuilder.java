package io.github.Redouane59.dz.model.generator.NVA;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.Optional;

public class NVASentenceBuilder extends AbstractSentenceBuilder {

  public Optional<AbstractSentence> generateRandomSentence(BodyArgs bodyArgs) {
    Tense          randomTense = WordPicker.getRandomTense(bodyArgs.getTenses());
    NVASentence    nvaSentence = new NVASentence();
    Optional<Noun> randomNoun  = WordPicker.pickRandomNoun(bodyArgs.getNounsFromIds());
    if (randomNoun.isEmpty()) {
      return Optional.empty();
    }
    nvaSentence.setNoun(randomNoun.get());
    nvaSentence.setTense(randomTense);
    Optional<Verb> randomVerb = WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense, VerbType.STATE);
    if (randomVerb.isEmpty()) {
      System.out.println("No randomVerb found in NVA");
      return Optional.empty();
    }
    nvaSentence.setVerb(randomVerb.get());
    Optional<Adjective> randomAdjective = WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds(), randomNoun.get().getWordType());
    if (randomAdjective.isEmpty()) {
      return Optional.empty();
    }
    nvaSentence.setAdjective(randomAdjective.get());
    nvaSentence.addFrTranslation(nvaSentence.buildSentenceValue(Lang.FR));
    nvaSentence.addDzTranslation(nvaSentence.buildSentenceValue(Lang.DZ));
    return Optional.of(nvaSentence);
  }

}
