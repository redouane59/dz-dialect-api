package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.word.Word;
import org.junit.jupiter.api.Test;

public class DisplayAdverbsTest {


  @Test
  public void displayAllAdverbs() {
    System.out.println("*** Adverbs ***");
    DB.ADVERBS.forEach(System.out::println);
  }

  @Test
  public void displayAllAdverbsCSV() {
    System.out.println("*** Adverbs ***");
    final StringBuilder line = new StringBuilder();
    DB.ADVERBS.stream().forEach(n ->
                                    line.append(n.getId())
                                        .append(",")
                                        .append(((Word) n.getValues().get(0)).getTranslationValue(Lang.FR))
                                        .append(",")
                                        .append(((Word) n.getValues().get(0)).getTranslationValue(Lang.DZ))
                                        .append("\n"));

    System.out.println(line);

  }

}
