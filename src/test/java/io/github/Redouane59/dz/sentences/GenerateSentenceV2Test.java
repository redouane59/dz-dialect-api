package io.github.Redouane59.dz.sentences;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.sentence.SentenceType;
import io.github.Redouane59.dz.model.sentence.V2.SentenceBuilder;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.word.Sentence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class GenerateSentenceV2Test {

  @Test
  public void testGenericSentenceBuilder() throws JsonProcessingException {
    int nbTries = 50;

    List<SentenceType> sentenceTypes = List.of(SentenceType.PVA);
    //  List<SentenceType> sentenceTypes = List.of(SentenceType.values());
    System.out.println();
    for (SentenceType sentenceType : sentenceTypes) {
      SentenceBuilder sentenceBuilder = sentenceType.getSentenceBuilder();
      for (int i = 0; i < nbTries; i++) {
        Optional<Sentence> sentence = sentenceBuilder.generate(GeneratorParameters.builder()
                                                                                  //                .verbs(Set.of("attendre"))
                                                                                  .tenses(Set.of(Tense.PRESENT)).build());
        if (sentence.isPresent()) {
          if (sentence.get().getDzTranslationAr() != null && !sentence.get().getDzTranslationAr().contains("null")) {
            System.out.println(sentence.get().getTranslationValue(Lang.FR) + " -> " + sentence.get().getTranslationValue(Lang.DZ)
                               + " | " + sentence.get().getDzTranslationAr());
          }
        } else {
          System.out.println("-");
        }
        // System.out.println(OBJECT_MAPPER.writeValueAsString(sentence));
      }
    }
  }
}
