package io.github.Redouane59.dz.function;

import static io.github.Redouane59.dz.function.helper.Config.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.Redouane59.dz.function.model.Lang;
import io.github.Redouane59.dz.function.model.Translation;
import io.github.Redouane59.dz.function.model.adjective.Adjective;
import io.github.Redouane59.dz.function.model.adjective.AdjectiveRoot;
import io.github.Redouane59.dz.function.model.generator.SentenceGenerator;
import io.github.Redouane59.dz.function.model.noun.NounRoot;
import io.github.Redouane59.dz.function.model.verb.Conjugation;
import io.github.Redouane59.dz.function.model.verb.Verb;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class RandomGeneratorTest {

  List<Verb>          verbs      = OBJECT_MAPPER.readValue(new File("src/main/resources/verbs.json"), new TypeReference<>() {
  });
  List<AdjectiveRoot> adjectives = OBJECT_MAPPER.readValue(new File("src/main/resources/adjectives.json"), new TypeReference<>() {
  });
  List<NounRoot>      nouns      = OBJECT_MAPPER.readValue(new File("src/main/resources/nouns.json"), new TypeReference<>() {
  });

  public RandomGeneratorTest() throws IOException {
  }

  @Test
  public void getRandomConjugationAndAdjective() {
    int occurrences = 10;
    for (int i = 0; i < occurrences; i++) {
      Verb          randomVerb          = verbs.get(new Random().nextInt(verbs.size()));
      Conjugation   randomConjugation   = randomVerb.getRandomConjugation();
      AdjectiveRoot randomAdjectiveRoot = adjectives.get(new Random().nextInt(adjectives.size()));
      Adjective
          randomAdjective =
          randomAdjectiveRoot.getAdjective(randomConjugation.getPerson().getGender(), randomConjugation.getPerson().isSingular());
      assertNotNull(randomConjugation);
      System.out.println(randomConjugation.getTranslation(Lang.DZ).get() + " " + randomAdjective.getTranslation(Lang.DZ).get()
                         + " - > " +
                         randomConjugation.getTranslation(Lang.FR).get() + " " + randomAdjective.getTranslation(Lang.FR).get());
    }
  }

  @Test
  public void getRandomConjugationAndPlace() {
    SentenceGenerator sentenceGenerator = new SentenceGenerator(verbs, adjectives, nouns);
    int               occurrences       = 10;
    for (int i = 0; i < occurrences; i++) {
      List<Translation> result = sentenceGenerator.generateRandomSentence();
      assertNotNull(result);
      System.out.println(Translation.printTranslations(result));
    }
  }
}
