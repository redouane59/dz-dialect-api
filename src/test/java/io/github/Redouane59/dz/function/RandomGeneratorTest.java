package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.generator.SentenceGenerator;
import io.github.Redouane59.dz.model.sentence.Sentence;
import io.github.Redouane59.dz.model.sentence.Sentences;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RandomGeneratorTest {

  @Test
  public void generateAllTest() {
    System.out.println("ALL");
    SentenceGenerator sentenceGenerator = new SentenceGenerator(BodyArgs.builder().count(30).build());
    Sentences         result            = sentenceGenerator.generateRandomSentences();
    for (Sentence sentence : result.getSentences()) {
      assertNotNull(sentence);
      System.out.println(Translation.printTranslations(sentence.getTranslations()));
    }
  }

  @Test
  public void generateNoVerbTest() {
    System.out.println("NO VERB");
    SentenceGenerator
        sentenceGenerator =
        new SentenceGenerator(BodyArgs.builder().count(20).wordTypes(List.of(WordType.PLACE, WordType.ADJECTIVE)).build());
    Sentences result = sentenceGenerator.generateRandomSentences();
    for (Sentence sentence : result.getSentences()) {
      assertNotNull(sentence);
      System.out.println(Translation.printTranslations(sentence.getTranslations()));
    }
  }

  @Test
  public void generateNoAdjectiveTest() {
    System.out.println("NO ADJ");
    SentenceGenerator
        sentenceGenerator =
        new SentenceGenerator(BodyArgs.builder().count(20).wordTypes(List.of(WordType.PLACE, WordType.VERB)).build());
    Sentences result = sentenceGenerator.generateRandomSentences();
    for (Sentence sentence : result.getSentences()) {
      assertNotNull(sentence);
      System.out.println(Translation.printTranslations(sentence.getTranslations()));
    }
  }

  @Test
  public void generateNoNounTest() {
    System.out.println("NO NOUN");
    SentenceGenerator
        sentenceGenerator =
        new SentenceGenerator(BodyArgs.builder().count(20).wordTypes(List.of(WordType.VERB, WordType.ADJECTIVE)).build());
    Sentences result = sentenceGenerator.generateRandomSentences();
    for (Sentence sentence : result.getSentences()) {
      assertNotNull(sentence);
      System.out.println(Translation.printTranslations(sentence.getTranslations()));
    }
  }

}
