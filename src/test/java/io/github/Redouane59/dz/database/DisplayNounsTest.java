package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.word.AbstractWord;
import org.junit.jupiter.api.Test;

public class DisplayNounsTest {

  @Test
  public void displayAllNouns() {
    System.out.println("*** Nouns ***");
    DB.NOUNS.stream().map(AbstractWord::getValues).forEach(o -> o.forEach(System.out::println));
  }

  @Test
  public void displayAllNounsCSV() {
    System.out.println("*** Nouns ***");
    final StringBuilder line = new StringBuilder();
    DB.NOUNS.forEach(n -> n.getValues().forEach(v ->
                                                    line.append(n.getId())
                                                        .append(",")
                                                        .append(v.isSingular())
                                                        .append(",")
                                                        .append(v.getTranslationValue(Lang.FR))
                                                        .append(",")
                                                        .append(v.getGender(Lang.FR))
                                                        .append(",")
                                                        .append(v.getTranslationValue(Lang.DZ))
                                                        .append(",")
                                                        .append(v.getGender(Lang.DZ))
                                                        .append("\n")));

    System.out.println(line);

  }

}
