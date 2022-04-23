package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.verb.GenericSuffixes.Suffix;
import org.junit.jupiter.api.Test;

public class SuffixTest {

  @Test
  public void testFrSuffixes() {
    Suffix suffix = DB.FR_SUFFIXES.getSuffix(Gender.X, Possession.I, true);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.I);
    assertTrue(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "me");
    assertEquals(suffix.getIndirectValue(), "me");

    suffix = DB.FR_SUFFIXES.getSuffix(Gender.X, Possession.YOU, true);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.YOU);
    assertTrue(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "te");
    assertEquals(suffix.getIndirectValue(), "te");

    suffix = DB.FR_SUFFIXES.getSuffix(Gender.M, Possession.OTHER, true);
    assertEquals(suffix.getGender(), Gender.M);
    assertEquals(suffix.getPossession(), Possession.OTHER);
    assertTrue(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "le");
    assertEquals(suffix.getIndirectValue(), "lui");

    suffix = DB.FR_SUFFIXES.getSuffix(Gender.F, Possession.OTHER, true);
    assertEquals(suffix.getGender(), Gender.F);
    assertEquals(suffix.getPossession(), Possession.OTHER);
    assertTrue(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "la");
    assertEquals(suffix.getIndirectValue(), "lui");

    suffix = DB.FR_SUFFIXES.getSuffix(Gender.X, Possession.I, false);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.I);
    assertFalse(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "nous");
    assertEquals(suffix.getIndirectValue(), "nous");

    suffix = DB.FR_SUFFIXES.getSuffix(Gender.X, Possession.YOU, false);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.YOU);
    assertFalse(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "vous");
    assertEquals(suffix.getIndirectValue(), "vous");

    suffix = DB.FR_SUFFIXES.getSuffix(Gender.X, Possession.OTHER, false);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.OTHER);
    assertFalse(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "les");
    assertEquals(suffix.getIndirectValue(), "leur");
  }

  @Test
  public void testDzSuffixes() {
    Suffix suffix = DB.DZ_SUFFIXES.getSuffix(Gender.X, Possession.I, true);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.I);
    assertTrue(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "ni");
    assertEquals(suffix.getIndirectValue(), "li");

    suffix = DB.DZ_SUFFIXES.getSuffix(Gender.X, Possession.YOU, true);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.YOU);
    assertTrue(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "nek");
    assertEquals(suffix.getIndirectValue(), "lek");

    suffix = DB.DZ_SUFFIXES.getSuffix(Gender.M, Possession.OTHER, true);
    assertEquals(suffix.getGender(), Gender.M);
    assertEquals(suffix.getPossession(), Possession.OTHER);
    assertTrue(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "nou");
    assertEquals(suffix.getIndirectValue(), "lou");

    suffix = DB.DZ_SUFFIXES.getSuffix(Gender.F, Possession.OTHER, true);
    assertEquals(suffix.getGender(), Gender.F);
    assertEquals(suffix.getPossession(), Possession.OTHER);
    assertTrue(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "nha");
    assertEquals(suffix.getIndirectValue(), "lha");

    suffix = DB.DZ_SUFFIXES.getSuffix(Gender.X, Possession.I, false);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.I);
    assertFalse(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "na");
    assertEquals(suffix.getIndirectValue(), "lna");

    suffix = DB.DZ_SUFFIXES.getSuffix(Gender.X, Possession.YOU, false);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.YOU);
    assertFalse(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "nkoum");
    assertEquals(suffix.getIndirectValue(), "lkoum");

    suffix = DB.DZ_SUFFIXES.getSuffix(Gender.X, Possession.OTHER, false);
    assertEquals(suffix.getGender(), Gender.X);
    assertEquals(suffix.getPossession(), Possession.OTHER);
    assertFalse(suffix.isSingular());
    assertEquals(suffix.getDirectValue(), "nhoum");
    assertEquals(suffix.getIndirectValue(), "lhoum");
  }
}
