package io.github.Redouane59.dz.model.generator.PV.NVA;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.VerbType;

public class NVASentenceBuilder extends AbstractSentenceBuilder {

  public AbstractSentence generateRandomSentence(BodyArgs bodyArgs) {
    Tense       randomTense = WordPicker.getRandomTense();
    NVASentence nvaSentence = new NVASentence();
    nvaSentence.setNoun(WordPicker.pickRandomNoun(bodyArgs.getNounsFromIds()));
    nvaSentence.setTense(randomTense);
    nvaSentence.setVerb(WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense, VerbType.STATE).get());
    nvaSentence.setAdjective(WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds()));
    nvaSentence.addFrTranslation(nvaSentence.buildSentenceValue(Lang.FR));
    nvaSentence.addDzTranslation(nvaSentence.buildSentenceValue(Lang.DZ));
    return nvaSentence;
  }

}
