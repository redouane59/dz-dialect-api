package io.github.Redouane59.dz.jsonFiles;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import org.junit.jupiter.api.Test;

public class DisplayTest {

  @Test
  public void displayAllAdjectives() {
    System.out.println("*** Adjectives ***");
    DB.ADJECTIVES.stream().map(AbstractWord::getValues).forEach(System.out::println);
  }

  @Test
  public void displayAllNouns() {
    System.out.println("*** Nouns ***");
    DB.NOUNS.stream().map(AbstractWord::getValues).forEach(System.out::println);
  }

  @Test
  public void displayAllAdverbs() {
    System.out.println("*** Adverbs ***");
    DB.ADVERBS.forEach(System.out::println);
  }

  @Test
  public void displayAllVerbs() {
    System.out.println("*** Verbs ***");
    DB.VERBS.stream().map(Verb::getConjugators)
            .forEach(c -> c.forEach(x -> x.getConjugations()
                                          .forEach(o -> System.out.println(AbstractSentence.cleanResponse(
                                              o.getDzTranslation()
                                              + " -> "
                                              + o.getPersonalPronoun()
                                                 .getTranslationValue(Lang.FR)
                                              + " "
                                              + o.getFrTranslation()
                                          )))));
  }
}
