package io.github.Redouane59.dz.helper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class VerbCSVTest {

  @Test
  public void parseTest() {
    String    fileName = "verb.csv";
    Set<Verb> verbs    = Verb.getVerbsFromCSV(fileName);

    Set<Conjugator>
        allConjugators =
        verbs.stream().map(Verb::getConjugators).flatMap(Collection::stream).collect(Collectors.toSet());
    assertTrue(allConjugators.stream().anyMatch(c -> c.getTense() == Tense.PRESENT));
    assertTrue(allConjugators.stream().anyMatch(c -> c.getTense() == Tense.PAST));
    Conjugation conjugation1 = verbs.stream().findAny().get().getConjugators().stream().findAny().get()
                                    .getConjugations().stream().filter(o -> o.getPossession() == Possession.I).findAny().get();
    assertNotNull(conjugation1.getTranslationValue(Lang.FR));
    assertNotNull(conjugation1.getTranslationValue(Lang.DZ));
    Conjugation conjugation2 = verbs.stream().findAny().get().getConjugators().stream().findAny().get()
                                    .getConjugations().stream().filter(o -> o.getPossession() == Possession.YOU)
                                    .filter(o -> o.getGender() == Gender.M || o.getGender() == Gender.X)
                                    .findAny().get();
    assertNotNull(conjugation2.getTranslationValue(Lang.FR));
    assertNotNull(conjugation2.getTranslationValue(Lang.DZ));
  }

}
