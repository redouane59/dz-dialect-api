package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.verb.Verb;
import org.junit.jupiter.api.Test;

public class DisplayVerbsTest {

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
