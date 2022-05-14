package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.word.AbstractWord;
import org.junit.jupiter.api.Test;

public class DisplayVerbsTest {

  @Test
  public void displayAllVerbs() {
    System.out.println("*** Verbs ***");
    DB.VERBS.stream().forEach(
        v -> v.getValues().forEach(
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
