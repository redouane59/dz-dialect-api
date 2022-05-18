package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import io.github.Redouane59.dz.model.word.Word;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

// @todo leur leurs missing
public class ArticleTest {

  @Test
  public void testDeserialize() throws IOException {
    AbstractWord article = Config.OBJECT_MAPPER.readValue(new File("./src/main/resources/articles/defined_articles.json"), AbstractWord.class);
    assertTrue(article.getValues().get(0) instanceof Word);
    assertTrue(article.getValues().get(0) instanceof GenderedWord);
    assertFalse(article.getValues().get(0) instanceof PossessiveWord);
    assertFalse(article.getValues().get(0) instanceof Conjugation);
  }

  @Test
  public void testDefinedArticles() {
    AbstractWord articles = DB.DEFINED_ARTICLES;
    assertNotNull(articles);
    articles.getValues().forEach(
        c -> System.out.println(c));
    //    System.out.println(c.getFrTranslation() + " -> " + c.getDzTranslation()));

  }

  @Test
  public void testPossessiveArticles() {
    AbstractWord articles = DB.POSSESSIVE_ARTICLES;
    assertNotNull(articles);
    for (GenderedWord c : articles.getValues().stream().map(o -> (GenderedWord) o).collect(Collectors.toList())) {
      System.out.println(c.getFrTranslation() + " -> " + c.getDzTranslation());
    }
  }

  @Test
  public void testUndefinedArticles() {
    AbstractWord articles = DB.UNDEFINED_ARTICLES;
    assertNotNull(articles);
    for (GenderedWord c : articles.getValues().stream().map(o -> (GenderedWord) o).collect(Collectors.toList())) {
      System.out.println(c.getFrTranslation() + " -> " + c.getDzTranslation());
    }
  }
}
