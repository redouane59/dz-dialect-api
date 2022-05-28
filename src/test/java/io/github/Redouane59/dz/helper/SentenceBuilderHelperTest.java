package io.github.Redouane59.dz.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.sentence.SentenceBuilderHelper;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import org.junit.jupiter.api.Test;

public class SentenceBuilderHelperTest {

  SentenceBuilderHelper helper = new SentenceBuilderHelper(GeneratorParameters.builder().build(), new SentenceSchema());

  @Test
  public void testSplitWords() {
    String sentence;
    sentence = "la femme est grande";
    assertEquals(4, helper.splitSentenceInWords(sentence).size());
    sentence = "l'homme est grand";
    assertEquals(4, helper.splitSentenceInWords(sentence).size());
    sentence = "il s'appelle Redouane";
    assertEquals(4, helper.splitSentenceInWords(sentence).size());
  }

}
