package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.model.Article;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import org.junit.jupiter.api.Test;

public class ArticleEnumTest {

  @Test
  public void test1() {
    Article le = Article.LE;
    assertEquals(le.getId(), "le");
    assertEquals(le.getGender(), Gender.M);
    assertTrue(le.isDefined());
    assertTrue(le.isSingular());
    assertEquals(le.getTranslationValue(Lang.FR, "_"), "le");
    assertEquals(le.getTranslationValue(Lang.DZ, "_"), "el");

    Article des = Article.DES;
    assertEquals(des.getId(), "des");
    assertEquals(des.getGender(), Gender.X);
    assertFalse(des.isDefined());
    assertFalse(des.isSingular());
    assertEquals(des.getTranslationValue(Lang.FR, "_"), "des");
    assertEquals(des.getTranslationValue(Lang.DZ, "_"), "");
  }
}
