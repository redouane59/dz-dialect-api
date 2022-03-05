package io.github.Redouane59.dz.database;

import io.github.Redouane59.dz.helper.DB;
import org.junit.jupiter.api.Test;

public class DisplayAdverbsTest {


  @Test
  public void displayAllAdverbs() {
    System.out.println("*** Adverbs ***");
    DB.ADVERBS.forEach(System.out::println);
  }

}
