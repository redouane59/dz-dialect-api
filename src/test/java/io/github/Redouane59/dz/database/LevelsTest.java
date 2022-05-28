package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.helper.GeneratorParameters;
import io.github.Redouane59.dz.model.level.Level;
import io.github.Redouane59.dz.model.sentence.SentenceBuilder;
import io.github.Redouane59.dz.model.word.Sentence;
import java.util.Optional;
import org.junit.jupiter.api.Test;

// @todo leur leurs missing
public class LevelsTest {

  @Test
  public void testDeserialize() {
    assertNotNull(DB.LEVELS);
    assertEquals(1, DB.LEVELS.get(0).getId());
    for (Level level : DB.LEVELS) {
      System.out.println("-----");
      System.out.println("level " + level.getId() + " -> " + level.getDescription());
      GeneratorParameters parameters = level.getParameters();
      SentenceBuilder
          sentenceBuilder =
          new SentenceBuilder(DB.SENTENCE_SCHEMAS.stream()
                                                 .filter(o -> parameters.getSentenceSchemas().stream().findFirst().get().equals(o.getId()))
                                                 .findFirst()
                                                 .get());
      for (int i = 0; i < 30; i++) {
        Optional<Sentence> sentence = sentenceBuilder.generate(parameters);
        assertTrue(sentence.isPresent());
        System.out.println(sentence.get());
      }
      System.out.println();
    }


  }
}
