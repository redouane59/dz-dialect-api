package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import org.junit.jupiter.api.Test;

// @todo leur leurs missing
public class ArticleTest {

  @Test
  public void testDefinedArticles() {
    AbstractWord articles = DB.DEFINED_ARTICLES;
    assertNotNull(articles);
    for (Conjugation c : articles.getValues()) {
      System.out.println(c.getFrTranslation() + " -> " + c.getDzTranslation());
    }
  }

  @Test
  public void testPossessiveArticles() {
    AbstractWord articles = DB.POSSESSIVE_ARTICLES;
    assertNotNull(articles);
    for (Conjugation c : articles.getValues()) {
      System.out.println(c.getFrTranslation() + " -> " + c.getDzTranslation());
    }
  }

  @Test
  public void testUndefinedArticles() {
    AbstractWord articles = DB.UNDEFINED_ARTICLES;
    assertNotNull(articles);
    for (Conjugation c : articles.getValues()) {
      System.out.println(c.getFrTranslation() + " -> " + c.getDzTranslation());
    }
  }
}
