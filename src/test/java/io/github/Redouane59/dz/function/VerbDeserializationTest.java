package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Redouane59.dz.function.model.Gender;
import io.github.Redouane59.dz.function.model.Lang;
import io.github.Redouane59.dz.function.model.Person;
import io.github.Redouane59.dz.function.model.Possession;
import io.github.Redouane59.dz.function.model.noun.WordType;
import io.github.Redouane59.dz.function.model.verb.Conjugation;
import io.github.Redouane59.dz.function.model.verb.Tense;
import io.github.Redouane59.dz.function.model.verb.Verb;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VerbDeserializationTest {

  private final ObjectMapper objectMapper = new ObjectMapper();


  @Test
  public void verbsDeserializationTest() {
    List<Verb> result = null;
    try {
      result = new ObjectMapper().readValue(new File("src/main/resources/verbs.json"), new TypeReference<>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("être", result.get(0).getFrValue());
    assertEquals(Tense.PAST, result.get(0).getConjugators().get(0).getTense());
    assertEquals(Person.P1, result.get(0).getConjugators().get(0).getConjugations().get(0).getPerson());
    assertEquals(WordType.PLACE, result.get(0).getComplements().get(0));

    // conjugation
    Conjugation conjugation = result.get(0).getConjugators().get(0).getConjugations().get(0);
    Person      person      = conjugation.getPerson();
    assertEquals(Person.P1, person);
    assertEquals(Possession.I, person.getPossession());
    assertEquals(Gender.X, person.getGender());
    assertEquals(true, person.isSingular());

    // translation
    assertEquals("j'étais", conjugation.getTranslation(Lang.FR).get());
    assertEquals(Lang.FR, conjugation.getTranslations().get(0).getLang());
  }
}
