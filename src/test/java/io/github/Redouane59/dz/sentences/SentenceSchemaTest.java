package io.github.Redouane59.dz.sentences;

import static io.github.Redouane59.dz.helper.Config.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.verb.Tense;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class SentenceSchemaTest {

  SentenceSchema nvSchema = OBJECT_MAPPER.readValue(new File("src/main/resources/sentences/nv_sentence.json"), SentenceSchema.class);

  public SentenceSchemaTest() throws IOException {
  }

  @Test
  public void testNVSchema() {
    assertEquals("NV", nvSchema.getId());
    assertEquals(WordType.ARTICLE, nvSchema.getFrSequence().get(0));
    assertEquals(WordType.NOUN, nvSchema.getFrSequence().get(1));
    assertEquals(WordType.VERB, nvSchema.getFrSequence().get(2));
    assertTrue(nvSchema.getTenses().contains(Tense.PAST));
    assertTrue(nvSchema.getTenses().contains(Tense.PAST2));
    assertTrue(nvSchema.getTenses().contains(Tense.PRESENT));
    assertTrue(nvSchema.getTenses().contains(Tense.FUTURE));
  }

}
