package io.github.Redouane59.dz.sentences;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.sentence.SentenceType;
import io.github.Redouane59.dz.model.sentence.V2.SentenceBuilder;
import io.github.Redouane59.dz.model.word.Sentence;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GenerateSentenceV2Test {

  @Test
  public void testGenericSentenceBuilder() throws JsonProcessingException {
    int nbTries = 20;

    List<SentenceType> sentenceTypes = List.of(SentenceType.PVN_DEP, SentenceType.PVN3_STA);
    System.out.println();
    for (SentenceType sentenceType : sentenceTypes) {
      SentenceBuilder sentenceBuilder = sentenceType.getSentenceBuilder();
      for (int i = 0; i < nbTries; i++) {
        Sentence sentence = sentenceBuilder.generate(GeneratorParameters.builder().build()).get();
        System.out.println(sentence);
        // System.out.println(OBJECT_MAPPER.writeValueAsString(sentence));
      }
    }
  }
}
