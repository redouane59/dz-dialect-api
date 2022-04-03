package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.word.AbstractWord;
import org.junit.jupiter.api.Test;

public class DisplayAdjectivesTest {

  @Test
  public void displayAllAdjectives() {
    System.out.println("*** Adjectives ***");
    DB.ADJECTIVES.stream().map(AbstractWord::getValues).forEach(o -> o.forEach(System.out::println));
  }

  @Test
  public void displayAllNounsCSV() {
    System.out.println("*** Adjectives ***");
    final StringBuilder line = new StringBuilder();
    DB.ADJECTIVES.forEach(a -> a.getValues().forEach(v ->
                                                         line.append(a.getId())
                                                             .append(",")
                                                             .append(v.isSingular())
                                                             .append(",")
                                                             .append(v.getGender())
                                                             .append(",")
                                                             .append(v.getTranslationValue(Lang.FR))
                                                             .append(",")
                                                             .append(v.getTranslationValue(Lang.DZ))
                                                             .append("\n")));

    System.out.println(line);

  }
}

