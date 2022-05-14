package io.github.Redouane59.dz.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @todo migrate like others
public class Articles {

  @Getter
  private static final Set<Article> articles = new HashSet<>();

  public Articles(String filePath) {
    try {
      File       file           = new File(filePath);
      JsonNode[] objectSuffixes = Config.OBJECT_MAPPER.readValue(file, JsonNode[].class);
      for (JsonNode jsonNode : objectSuffixes) {
        Article article = new Article();
        article.setGender(Gender.valueOf(jsonNode.get("gender").asText()));
        article.setPossession(Possession.valueOf(jsonNode.get("possession").asText()));
        article.setSingular(jsonNode.get("singular").asBoolean());
        article.setDefined(jsonNode.get("defined").asBoolean());
        article.setTranslations(Config.OBJECT_MAPPER.readValue(jsonNode.get("translations").toString(), new TypeReference<>() {
        }));
        articles.add(article);
      }
    } catch (
        IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static Optional<Article> getArticleByCriterion(Gender gender, Possession possession, boolean singular, boolean defined) {
    return articles.stream()
                   .filter(a -> a.getPossession() == possession)
                   .filter(a -> a.isSingular() == singular)
                   .filter(a -> a.isDefined() == defined)
                   .filter(a -> a.getGender() == gender || a.getGender() == Gender.X || gender == Gender.X)
                   .findAny();
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class Article extends PossessiveWord {

    private boolean defined;
  }
}
