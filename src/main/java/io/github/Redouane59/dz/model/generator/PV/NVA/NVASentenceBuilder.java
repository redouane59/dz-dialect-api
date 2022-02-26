package io.github.Redouane59.dz.model.generator.PV.PVA;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.generator.WordPicker;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.VerbType;

public class PVASentenceBuilder extends AbstractSentenceBuilder {

  public AbstractSentence generateRandomSentence(BodyArgs bodyArgs) {
    Tense       randomTense = WordPicker.getRandomTense();
    PVASentence PVASentence = new PVASentence();
    PVASentence.setPersonalProunoun(PersonalProunoun.getRandomPersonalPronoun());
    PVASentence.setTense(randomTense);
    PVASentence.setVerb(WordPicker.pickRandomVerb(bodyArgs.getVerbsFromIds(), randomTense, VerbType.STATE).get());
    PVASentence.setAdjective(WordPicker.pickRandomAdjective(bodyArgs.getAdjectivesFromIds()));
    PVASentence.addFrTranslation(PVASentence.buildSentenceValue(Lang.FR));
    PVASentence.addDzTranslation(PVASentence.buildSentenceValue(Lang.DZ));
    return PVASentence;
  }

}
