package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import org.junit.jupiter.api.Test;

public class PersonalPronounsEnumTest {

  @Test
  public void test1() {
    PersonalProunoun i = PersonalProunoun.I;
    assertEquals(i.getPossession(), Possession.I);
    assertTrue(i.isSingular());
    assertEquals(i.getGender(), Gender.X);
    assertEquals(i.getTranslationValue(Lang.FR), "je");
    assertEquals(i.getTranslationValue(Lang.DZ), "ana");

    PersonalProunoun y = PersonalProunoun.YOU_M;
    assertEquals(y.getPossession(), Possession.YOU);
    assertTrue(y.isSingular());
    assertEquals(y.getGender(), Gender.M);
    assertEquals(y.getTranslationValue(Lang.FR), "tu");
    assertEquals(y.getTranslationValue(Lang.DZ), "enta");
  }
}
