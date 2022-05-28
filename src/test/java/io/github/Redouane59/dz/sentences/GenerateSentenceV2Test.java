package io.github.Redouane59.dz.sentences;

import static io.github.Redouane59.dz.model.sentence.SentenceBuilder.RANDOM;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.helper.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.sentence.SentenceBuilderV2;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.word.Sentence;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class GenerateSentenceV2Test {

  @Test
  public void testGenericSentenceBuilder() throws JsonProcessingException {
    int nbTries = 2;
    System.out.println();
    SentenceSchema    sentenceSchema  = DB.SENTENCE_SCHEMAS.stream().skip(RANDOM.nextInt(DB.SENTENCE_SCHEMAS.size())).findAny().get();
    SentenceBuilderV2 sentenceBuilder = new SentenceBuilderV2(sentenceSchema);
    for (int i = 0; i < nbTries; i++) {
      Optional<Sentence> sentence = sentenceBuilder.generate(GeneratorParameters.builder().build());
      if (sentence.isPresent()) {
        if (sentence.get().getDzTranslationAr() != null && !sentence.get().getDzTranslationAr().contains("null")) {
          System.out.println(sentence.get().getTranslationValue(Lang.FR) + " -> " + sentence.get().getTranslationValue(Lang.DZ));
        }
      }
    }
  }
}
