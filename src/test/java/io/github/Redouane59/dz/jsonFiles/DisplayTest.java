package io.github.Redouane59.dz.jsonFiles;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.word.GenderedWord;
import org.junit.jupiter.api.Test;

public class DisplayTest {

  @Test
  public void displayAllAdjectives() {
    System.out.println("*** Adjectives ***");
    for (Adjective adjective : DB.ADJECTIVES) {
      for (GenderedWord word : adjective.getValues()) {
        System.out.println(word);
      }
    }
  }

  @Test
  public void displayAllNouns() {
    System.out.println("*** Nouns ***");
    for (Noun adjective : DB.NOUNS) {
      for (GenderedWord word : adjective.getValues()) {
        System.out.println(word);
      }
    }
  }
}
