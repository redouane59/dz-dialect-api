package io.github.Redouane59.dz.sentences;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.sentence.PVSO.PVSOSentenceBuilder;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class PVSOSentenceBuilderTest {

  @Test
  public void generateSentences() {
    int nbTests = 60;
    for (int i = 0; i < nbTests; i++) {
      PVSOSentenceBuilder sentenceBuilder = new PVSOSentenceBuilder();
      Optional<AbstractSentence> sentence = sentenceBuilder.generateRandomSentence(
          GeneratorParameters.builder().build());
      assertTrue(sentence.isPresent());
      assertNotNull(sentence.get().buildSentenceValue(Lang.DZ));
      assertNotNull(sentence.get().buildSentenceValue(Lang.FR));
      System.out.println(sentence.get());
    }
  }

}
