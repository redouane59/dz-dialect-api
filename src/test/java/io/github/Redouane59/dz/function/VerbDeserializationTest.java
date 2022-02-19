package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VerbDeserializationTest {

  @Test
  public void verbsDeserializationTest() {
    List<Verb> result = null;
    try {
      result = Config.OBJECT_MAPPER.readValue(new File("src/main/resources/verbs.json"), new TypeReference<>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    Verb verb1 = result.get(0);
    // verb
    assertEquals("être", verb1.getId());
    assertTrue(verb1.getPossibleComplements().contains(WordType.PLACE));
    assertTrue(verb1.getPossibleComplements().contains(WordType.ADJECTIVE));

    // conjugator
    assertEquals(Tense.PAST, verb1.getConjugators().get(0).getTense());
    assertEquals(Tense.PRESENT, verb1.getConjugators().get(1).getTense());

    // conjugation
    PossessiveWord conjugation1 = verb1.getConjugators().get(0).getConjugations().get(0);
    assertEquals(Possession.I, conjugation1.getPossession());
    assertEquals(Gender.X, conjugation1.getGender());
    assertTrue(conjugation1.isSingular());

    // translation
    assertEquals("j'étais", conjugation1.getFrTranslation());
    assertEquals("kount", conjugation1.getDzTranslation());
  }
}
