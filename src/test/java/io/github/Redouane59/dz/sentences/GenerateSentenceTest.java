package io.github.Redouane59.dz.sentences;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.helper.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.sentence.SentenceBuilder;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.verb.RootTense;
import io.github.Redouane59.dz.model.word.Sentence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class GenerateSentenceTest {

  @Test
  public void testGenericSentenceBuilder() throws JsonProcessingException {
    int nbTries = 10;

    List<String> schemaIds = List.of("PVS");
    System.out.println();
    for (String schemaId : schemaIds) {
      SentenceSchema  sentenceSchema  = DB.SENTENCE_SCHEMAS.stream().filter(o -> o.getId().equals(schemaId)).findAny().get();
      SentenceBuilder sentenceBuilder = new SentenceBuilder(sentenceSchema);
      for (int i = 0; i < nbTries; i++) {
        Optional<Sentence> sentence = sentenceBuilder.generate(GeneratorParameters.builder().possibleNegation(true)
                                                                                  .possibleAffirmation(true)
                                                                                  //                .verbs(Set.of("attendre"))
                                                                                  .tenses(Set.of(RootTense.PRESENT)).build());
        if (sentence.isPresent()) {
          if (sentence.get().getDzTranslationAr() != null && !sentence.get().getDzTranslationAr().contains("null")) {
            System.out.println(sentence.get().getTranslationValue(Lang.FR) + " -> " + sentence.get().getTranslationValue(Lang.DZ)
                               + " | " + sentence.get().getDzTranslationAr()
            );
          }
        } else {
          System.out.println("-");
        }
        // System.out.println(OBJECT_MAPPER.writeValueAsString(sentence));
      }
    }
  }
}
