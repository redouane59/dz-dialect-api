package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.noun.NounType;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class NounDeserializationTest {

  public NounDeserializationTest() throws IOException {
  }

  @Test
  public void nounDeserializationTest() {

    Noun noun1 = DB.NOUNS.stream().filter(o -> o.getId().equals("maison")).findAny().get();
    assertEquals("maison", noun1.getId());
    assertEquals(2, noun1.getValues().size());
    assertTrue(noun1.getValues().get(0).isSingular());
    assertFalse(noun1.getValues().get(1).isSingular());
    assertEquals(Gender.F, noun1.getValues().get(0).getGender());
    assertEquals("maison", noun1.getValues().get(0).getFrTranslation());
    assertEquals("dar", noun1.getValues().get(0).getDzTranslation());
    assertEquals(WordType.NOUN, noun1.getWordType());
    assertEquals(NounType.PLACE, noun1.getNounTypes().stream().findFirst().get());
  }

}
