package io.github.Redouane59.dz.function;

import static io.github.Redouane59.dz.function.helper.Config.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.Redouane59.dz.function.model.Conjugation;
import io.github.Redouane59.dz.function.model.Verb;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class RandomGeneratorTest {

  List<Verb> verbs = OBJECT_MAPPER.readValue(new File("src/main/resources/verbs.json"), new TypeReference<>() {
  });

  public RandomGeneratorTest() throws IOException {
  }

  @Test
  public void getRandomConjugation() {
    int occurrences = 10;
    for (int i = 0; i < occurrences; i++) {
      //verb
      int         randomIndex = new Random().nextInt(verbs.size());
      Verb        verb        = verbs.get(randomIndex);
      Conjugation conjugation = verb.getRandomConjugation();
      assertNotNull(conjugation);
      System.out.println(conjugation.getTranslation());
    }
  }

  @Test
  public void generateRandomSentences() {

  }
}
