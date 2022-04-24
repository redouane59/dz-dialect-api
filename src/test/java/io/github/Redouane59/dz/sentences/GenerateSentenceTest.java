package io.github.Redouane59.dz.sentences;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.sentence.V2.GenericSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.V2.NVSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.V2.PVSentenceBuilder;
import io.github.Redouane59.dz.model.word.Sentence;
import org.junit.jupiter.api.Test;

public class GenerateSentenceTest {

  @Test
  public void testNVSentenceBuilder() {
    NVSentenceBuilder sentenceBuilder = new NVSentenceBuilder();
    for (int i = 0; i < 50; i++) {
      Sentence sentence = sentenceBuilder.generate(GeneratorParameters.builder().build()).get();
      //System.out.println(OBJECT_MAPPER.writeValueAsString(sentence));
      System.out.println(sentence);
    }
  }

  @Test
  public void testPVSentenceBuilder() {
    PVSentenceBuilder sentenceBuilder = new PVSentenceBuilder();
    for (int i = 0; i < 50; i++) {
      Sentence sentence = sentenceBuilder.generate(GeneratorParameters.builder().build()).get();
      System.out.println(sentence);
    }
  }

  @Test
  public void testGenericSentenceBuilder() {
    int                    nbTries         = 30;
    GenericSentenceBuilder sentenceBuilder = new GenericSentenceBuilder("src/main/resources/sentences/nva_sentence.json");
    for (int i = 0; i < nbTries; i++) {
      Sentence sentence = sentenceBuilder.generate(GeneratorParameters.builder().build()).get();
      System.out.println(sentence);
    }
 /*   System.out.println("------------");
    sentenceBuilder = new GenericSentenceBuilder("src/main/resources/sentences/pv_sentence.json");
    for (int i = 0; i < nbTries; i++) {
      Sentence sentence = sentenceBuilder.generate(GeneratorParameters.builder().build()).get();
      System.out.println(sentence);
    }
    System.out.println("------------");
    sentenceBuilder = new GenericSentenceBuilder("src/main/resources/sentences/v_sentence.json");
    for (int i = 0; i < nbTries; i++) {
      Optional<Sentence> sentence = sentenceBuilder.generate(GeneratorParameters.builder().build());
      if (sentence.isPresent()) {
        System.out.println(sentence.get());
      } else {
        System.out.println("empty sentence");
      }
    }*/
  }
}
