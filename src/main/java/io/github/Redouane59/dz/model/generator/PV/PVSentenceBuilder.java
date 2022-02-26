package io.github.Redouane59.dz.model.generator.PV;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Tense;

public class PVSentenceBuilder extends AbstractSentenceBuilder {

  public AbstractSentence generateRandomSentence(BodyArgs bodyArgs) {
    Tense      randomTense      = WordPicker.getRandomTense();
    PVSentence randomPVSentence = new PVSentence();
    randomPVSentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun());
    randomPVSentence.setTense(randomTense);
    randomPVSentence.setVerb(WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense).get());
    randomPVSentence.addFrTranslation(randomPVSentence.buildSentenceValue(Lang.FR));
    randomPVSentence.addDzTranslation(randomPVSentence.buildSentenceValue(Lang.DZ));
    return randomPVSentence;
  }

}
