package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class PersonalPronounTest {

  @Test
  public void testPronouns() {
   /* PersonalPronoun pronouns = DB.PERSONAL_PRONOUNS;
    assertNotNull(pronouns);
    for (Conjugation pronoun : pronouns.getValues()) {
      assertNotNull(pronoun);
      System.out.println(pronoun.getFrTranslation() + " / " + pronoun.getDzTranslation());
    } */

    Set<AbstractWord> x = DB.PERSONAL_PRONOUNS_V3;
    System.out.println(x);

  }
}
