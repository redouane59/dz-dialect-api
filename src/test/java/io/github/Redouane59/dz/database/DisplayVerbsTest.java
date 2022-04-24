package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.verb.PersonalPronouns;
import io.github.Redouane59.dz.model.verb.Verb;
import org.junit.jupiter.api.Test;

public class DisplayVerbsTest {

  @Test
  public void displayAllVerbs() {
    System.out.println("*** Verbs ***");
    DB.VERBS.stream().map(Verb::getConjugators)
            .forEach(c -> c.forEach(x -> x.getConjugations()
                                          .forEach(o -> System.out.println(
                                              o.getDzTranslation()
                                              + " -> "
                                              + PersonalPronouns.getPronounByCriterion(o.getGender(), o.getPossession(), o.isSingular()).get()
                                                                .getTranslationValue(Lang.FR)
                                              + " "
                                              + o.getFrTranslation()
                                          ))));
  }

  @Test
  public void displayAllVerbsCSV() {
    System.out.println("*** Verbs ***");
    final StringBuilder line = new StringBuilder();

    DB.VERBS.forEach(v -> v.getConjugators().forEach(
        j -> j.getConjugations().forEach(
            o -> line.append(
                v.getId()
                + ","
                + j.getTense()
                + ","
                + PersonalPronouns.getPronounByCriterion(o.getGender(), o.getPossession(), o.isSingular()).get().getTranslationValue(Lang.FR)
                + "/"
                + PersonalPronouns.getPronounByCriterion(o.getGender(), o.getPossession(), o.isSingular()).get().getTranslationValue(Lang.DZ)
                + ","
                + o.getFrTranslation()
                + ","
                + o.getDzTranslation()
                + "\n"
            )
        )
    ));
    System.out.println(line);
  }

}
