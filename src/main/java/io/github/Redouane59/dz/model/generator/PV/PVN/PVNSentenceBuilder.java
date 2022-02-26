package io.github.Redouane59.dz.model.generator.PV.PVN;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Tense;

public class PVNSentenceBuilder extends AbstractSentenceBuilder {

  public AbstractSentence generateRandomSentence(BodyArgs bodyArgs) {
    Tense       randomTense       = WordPicker.getRandomTense();
    PVNSentence randomPVNSentence = new PVNSentence();
    randomPVNSentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun());
    randomPVNSentence.setTense(randomTense);
    randomPVNSentence.setVerb(WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense).get());
    randomPVNSentence.setNoun(WordPicker.pickRandomNoun(bodyArgs.getNounsFromIds(), WordType.PLACE).get());
    randomPVNSentence.addFrTranslation(randomPVNSentence.buildSentenceValue(Lang.FR));
    randomPVNSentence.addDzTranslation(randomPVNSentence.buildSentenceValue(Lang.DZ));
    return randomPVNSentence;
  }

}
