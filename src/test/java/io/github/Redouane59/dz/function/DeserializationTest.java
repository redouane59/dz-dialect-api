package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Redouane59.dz.function.model.Conjugation;
import io.github.Redouane59.dz.function.model.Gender;
import io.github.Redouane59.dz.function.model.Person;
import io.github.Redouane59.dz.function.model.Possession;
import io.github.Redouane59.dz.function.model.Tense;
import io.github.Redouane59.dz.function.model.Translation;
import io.github.Redouane59.dz.function.model.Verb;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DeserializationTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void translationDeserializationTest() {
    String json = "{\n"
                  + "              \"fr_value\": \"j'étais\",\n"
                  + "              \"dz_value\": \"kount\"\n"
                  + "            }";
    Translation result = null;
    try {
      result = objectMapper.readValue(json, Translation.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    assertEquals("j'étais", result.getFrValue());
    assertEquals("kount", result.getDzValue());
  }

  @Test
  public void conjugationDeserializationTest() {
    String json = "{\n"
                  + "            \"person\": \"P1\",\n"
                  + "            \"translation\": {\n"
                  + "              \"fr_value\": \"j'étais\",\n"
                  + "              \"dz_value\": \"kount\"\n"
                  + "            }\n"
                  + "          }";
    Conjugation result = null;
    try {
      result = objectMapper.readValue(json, Conjugation.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    assertEquals(Person.P1, result.getPerson());
    assertEquals(Possession.I, result.getPerson().getPossession());
    assertEquals(Gender.X, result.getPerson().getGender());
    assertEquals(true, result.getPerson().isSingular());
    assertEquals("j'étais", result.getTranslation().getFrValue());
    assertEquals("kount", result.getTranslation().getDzValue());
  }

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

  }
}
