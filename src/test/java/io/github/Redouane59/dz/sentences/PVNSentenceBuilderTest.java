package io.github.Redouane59.dz.sentences;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.PVN.PVNSentenceBuilder;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class PVNSentenceBuilderTest {

  PVNSentenceBuilder pvnSentenceBuilder = new PVNSentenceBuilder();

  @Test
  public void generateSentences() {
    int nbTests = 10;
    for (int i = 0; i < nbTests; i++) {
      Optional<AbstractSentence> sentence = pvnSentenceBuilder.generateRandomSentence(BodyArgs.builder().build());
      assertTrue(sentence.isPresent());
      assertNotNull(sentence.get().buildSentenceValue(Lang.DZ));
      assertNotNull(sentence.get().buildSentenceValue(Lang.FR));
      System.out.println(sentence.get().buildSentenceValue(Lang.DZ) + " -> " + sentence.get().buildSentenceValue(Lang.FR));
    }
  }
}
