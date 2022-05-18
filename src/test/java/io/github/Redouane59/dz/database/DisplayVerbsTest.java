package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import org.junit.jupiter.api.Test;

public class DisplayVerbsTest {

  @Test
  public void displayAllVerbs() {
    System.out.println("*** Verbs ***");
    for (Verb ver : DB.VERBS) {
      for (Object w : ver.getValues()) {
        System.out.println(w);
        if (w instanceof Conjugation) {
          Conjugation g = (Conjugation) w;
          System.out.println(g);
        } else {
          System.err.println("not an instance!");
        }
      }
    }
    DB.VERBS.stream().forEach(
        v -> v.getValues().stream().map(o -> (Conjugation) o).forEach(
            o -> System.out.println(
                o.getTense() + " | "
                + AbstractWord.getPersonalPronoun(o.getGender(),
                                                  o.isSingular(),
                                                  o.getPossession()).get().getFrTranslation()
                + " "
                + o.getFrTranslation()
                + " - > "
                + AbstractWord.getPersonalPronoun(o.getGender(),
                                                  o.isSingular(),
                                                  o.getPossession()).get().getDzTranslation()
                + " "
                + o.getDzTranslation()
            )
        )
    );

  }

}
