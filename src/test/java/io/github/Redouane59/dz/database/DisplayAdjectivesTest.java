package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.word.AbstractWord;
import org.junit.jupiter.api.Test;

public class DisplayAdjectivesTest {

  @Test
  public void displayAllAdjectives() {
    System.out.println("*** Adjectives ***");
    DB.ADJECTIVES.stream().map(AbstractWord::getValues).forEach(o -> o.forEach(System.out::println));
  }
}
