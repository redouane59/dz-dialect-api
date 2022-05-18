package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.Noun;
import io.github.Redouane59.dz.model.complement.NounType;
import io.github.Redouane59.dz.model.word.GenderedWord;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class NounDeserializationTest {

  public NounDeserializationTest() throws IOException {
  }

  @Test
  public void nounDeserializationTest() {

    Noun noun1 = DB.NOUNS.stream().filter(o -> o.getId().equals("maison")).findAny().get();
    assertEquals("maison", noun1.getId());
    assertEquals(2, noun1.getValues().size());
    List<GenderedWord> values = noun1.getValues().stream().map(o -> (GenderedWord) o).collect(Collectors.toList());
    assertTrue(values.get(0).isSingular());
    assertFalse(values.get(1).isSingular());
    assertEquals(Gender.F, values.get(0).getGender());
    assertEquals("maison", values.get(0).getFrTranslation());
    assertEquals("dar", values.get(0).getDzTranslation());
    assertEquals(WordType.NOUN, noun1.getWordType());
    assertEquals(NounType.PLACE, noun1.getNounTypes().stream().findFirst().get());
  }

}
