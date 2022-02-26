package io.github.Redouane59.dz.function.sentences;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.PV.PVSentenceBuilder;
import org.junit.jupiter.api.Test;

public class PVSentenceBuilderTest {

  PVSentenceBuilder pvSentenceBuilder = new PVSentenceBuilder();

  @Test
  public void generateSentences() {
    int nbTests = 10;
    for (int i = 0; i < nbTests; i++) {
      AbstractSentence sentence = pvSentenceBuilder.generateRandomSentence(BodyArgs.builder().build());
      assertNotNull(sentence.buildSentenceValue(Lang.DZ));
      assertNotNull(sentence.buildSentenceValue(Lang.FR));
      System.out.println(sentence.getTranslationValue(Lang.DZ) + " -> " + sentence.getTranslationValue(Lang.FR));
    }
  }
}
