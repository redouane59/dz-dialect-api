package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import org.junit.jupiter.api.Test;

public class SuffixTest {

  @Test
  public void testDirectSuffixes() {
    AbstractWord suffixes = DB.DIRECT_SUFFIXES;
    assertNotNull(suffixes);
    assertTrue(suffixes.getValues().size() > 0);
    for (Conjugation suffix : suffixes.getValues()) {
      System.out.println(suffix.getFrTranslation() + " -> " + suffix.getDzTranslation());
    }
  }

  @Test
  public void testIndirectSuffixes() {
    AbstractWord suffixes = DB.INDIRECT_SUFFIXES;
    assertNotNull(suffixes);
    assertTrue(suffixes.getValues().size() > 0);
    for (Conjugation suffix : suffixes.getValues()) {
      System.out.println(suffix.getFrTranslation() + " -> " + suffix.getDzTranslation());
    }
  }
}
