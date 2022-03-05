package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class VerbDeserializationTest {

  @Test
  public void verbsDeserializationTest() {
    Verb verb = null;
    try {
      verb = Config.OBJECT_MAPPER.readValue(new File("src/main/resources/verbs/être.json"), Verb.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // verb
    assertEquals("être", verb.getId());
    assertTrue(verb.getPossibleComplements().contains(NounType.PLACE));
    assertTrue(verb.getPossibleComplements().contains(NounType.PERSON));

    // conjugator
    assertEquals(Tense.PAST, verb.getConjugators().get(0).getTense());
    assertEquals(Tense.PRESENT, verb.getConjugators().get(1).getTense());

    // conjugation
    PossessiveWord conjugation1 = verb.getConjugators().get(0).getConjugations().get(0);
    assertEquals(Possession.I, conjugation1.getPossession());
    assertEquals(Gender.X, conjugation1.getGender());
    assertTrue(conjugation1.isSingular());

    // translation
    assertEquals("étais", conjugation1.getFrTranslation());
    assertEquals("kount", conjugation1.getDzTranslation());
  }
}
