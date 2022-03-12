package io.github.Redouane59.dz.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.helper.Config;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

@Getter
/*
Translation values inserted through articles.json file
 */
public enum Article {
  LE,
  LA,
  LES,
  UN,
  UNE,
  DES;

  private Gender            gender;
  private boolean           defined;
  private boolean           singular;
  private List<Translation> translations;


  Article() {
    try {
      File       file              = new File("src/main/resources/other/articles.json");
      JsonNode[] personalProunouns = Config.OBJECT_MAPPER.readValue(file, JsonNode[].class);
      for (JsonNode jsonNode : personalProunouns) {
        if (jsonNode.get("id").asText().equals(this.name())) {
          this.gender       = Gender.valueOf(jsonNode.get("gender").asText());
          this.defined      = jsonNode.get("defined").asBoolean();
          this.singular     = jsonNode.get("singular").asBoolean();
          this.translations = Config.OBJECT_MAPPER.readValue(jsonNode.get("translations").toString(), new TypeReference<List<Translation>>() {
          });
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static Optional<Article> getArticle(Gender gender, boolean singular, boolean defined) {
    return Arrays.stream(Article.values())
                 .filter(o -> o.isSingular() == singular)
                 .filter(o -> o.getGender() == gender || o.getGender() == Gender.X)
                 .filter(o -> o.isDefined() == defined)
                 .findAny();
  }

  public String getTranslationValue(Lang lang, String nextWord) {
    Translation translation = this.translations.stream().filter(o -> o.getLang() == lang).findAny().orElse(new Translation(lang, ""));
    if (lang == Lang.FR && Config.VOWELS.contains(nextWord.charAt(0)) && this.gender != Gender.X) {
      return "l'";
    } else if (lang.getRootLang() == RootLang.AR) {
      if (this.gender == Gender.X) {
        return "";
      } else if (Config.CONSONANTS.contains(nextWord.substring(0, 1))) {
        return "e" + nextWord.charAt(0);
      }
    }
    return translation.getValue();
  }

}
