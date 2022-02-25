package io.github.Redouane59.dz.function;

import static io.github.Redouane59.dz.helper.Config.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class NounDeserializationTest {

  List<Noun> result = OBJECT_MAPPER.readValue(new File("src/main/resources/nouns.json"), new TypeReference<>() {
  });

  public NounDeserializationTest() throws IOException {
  }

  @Test
  public void nounDeserializationTest() {

    Noun noun1 = result.get(0);
    assertEquals("maison", noun1.getId());
    assertEquals(2, noun1.getValues().size());
    assertTrue(noun1.getValues().get(0).isSingular());
    assertFalse(noun1.getValues().get(1).isSingular());
    assertEquals(Gender.F, noun1.getValues().get(0).getGender());
    assertEquals("maison", noun1.getValues().get(0).getFrTranslation());
    assertEquals("dar", noun1.getValues().get(0).getDzTranslation());
    assertEquals(WordType.PLACE, noun1.getWordType());
  }

}
