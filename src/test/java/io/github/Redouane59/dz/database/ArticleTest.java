package io.github.Redouane59.dz.database;

import static io.github.Redouane59.dz.model.sentence.V2.SentenceBuilder.RANDOM;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Articles;
import io.github.Redouane59.dz.model.Articles.Article;
import io.github.Redouane59.dz.model.Lang;
import org.junit.jupiter.api.Test;

public class ArticleTest {

  @Test
  public void testArticles() {
    Articles articles = DB.ARTICLES;
    assertNotNull(articles);
    Article randomArticle = Articles.getArticles().stream().skip(RANDOM.nextInt(Articles.getArticles().size())).findFirst().get();
    assertNotNull(randomArticle.getGender());
    assertNotNull(randomArticle.getPossession());
    assertNotNull(randomArticle.getTranslations());
    assertNotNull(randomArticle.getTranslationValue(Lang.FR));
    assertNotNull(randomArticle.getTranslationValue(Lang.DZ));
  }
}
