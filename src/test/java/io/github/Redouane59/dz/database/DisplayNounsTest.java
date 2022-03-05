package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.word.AbstractWord;
import org.junit.jupiter.api.Test;

public class DisplayNounsTest {

  @Test
  public void displayAllNouns() {
    System.out.println("*** Nouns ***");
    DB.NOUNS.stream().map(AbstractWord::getValues).forEach(o -> o.forEach(System.out::println));
  }

}
