package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.generator.SentenceGenerator;
import io.github.Redouane59.dz.model.sentence.Sentence;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RandomGeneratorTest {

  @Test
  public void generateAllTest() {
    System.out.println("ALL");
    SentenceGenerator sentenceGenerator = new SentenceGenerator(BodyArgs.builder().count(10).build());
    List<Sentence>    result            = sentenceGenerator.generateRandomSentences();
    for (Sentence sentence : result) {
      assertNotNull(sentence);
      System.out.println(Translation.printTranslations(sentence.getTranslations()));
    }
  }

  @Test
  public void testSerializeResponse() throws JsonProcessingException {
    SentenceGenerator sentenceGenerator = new SentenceGenerator();
    String            result            = Config.OBJECT_MAPPER.writeValueAsString(sentenceGenerator.generateRandomSentences());
    assertNotNull(result);
  }

}
