package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.verb.SuffixEnum;
import org.junit.jupiter.api.Test;

public class SuffixTest {

  @Test
  public void testSuffixes() {
    SuffixEnum suffix = SuffixEnum.I_D;
    assertEquals(suffix.getSuffix().getGender(), Gender.X);
    assertEquals(suffix.getSuffix().getPossession(), Possession.I);
    assertTrue(suffix.getSuffix().isSingular());
    assertTrue(suffix.getSuffix().isDirect());
    assertEquals(suffix.getSuffix().getTranslationValue(Lang.FR), "me");
    assertEquals(suffix.getSuffix().getTranslationValue(Lang.DZ), "ni");

    suffix = SuffixEnum.HE_I;
    assertEquals(suffix.getSuffix().getGender(), Gender.M);
    assertEquals(suffix.getSuffix().getPossession(), Possession.OTHER);
    assertTrue(suffix.getSuffix().isSingular());
    assertFalse(suffix.getSuffix().isDirect());
    assertEquals(suffix.getSuffix().getTranslationValue(Lang.FR), "lui");
    assertEquals(suffix.getSuffix().getTranslationValue(Lang.DZ), "lou");
  }
}
