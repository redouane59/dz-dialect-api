package io.github.Redouane59.dz.model.question;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.Getter;

@Getter
/*
Translation values inserted through question.json file
 */
public enum Question {

  WHO,
  WHEN,
  WHERE,
  HOW,
  WHY,
  HOW_MANY,
  WHAT,
  WITH_WHO;

  private static final List<Question>    VALUES = Collections.unmodifiableList(Arrays.asList(values()));
  private              List<Translation> translations;

  Question() {
    try {
      File       file              = new File("src/main/resources/other/questions.json");
      JsonNode[] personalProunouns = Config.OBJECT_MAPPER.readValue(file, JsonNode[].class);
      for (JsonNode jsonNode : personalProunouns) {
        if (jsonNode.get("id").asText().equals(this.name())) {
          this.translations = Config.OBJECT_MAPPER.readValue(jsonNode.get("translations").toString(), new TypeReference<List<Translation>>() {
          });
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static Question getRandomInterrogativeProunoun() {
    return VALUES.get(new Random().nextInt(VALUES.size()));
  }

  // @todo duplicate
  public String getTranslationValue(Lang lang) {
    return getTranslations()
        .stream().filter(o -> o.getLang() == lang).findAny().get().getValue();
  }

}
