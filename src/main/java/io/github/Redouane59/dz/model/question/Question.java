package io.github.Redouane59.dz.model.question;

import static io.github.Redouane59.dz.model.sentence.WordPicker.RANDOM;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.word.Word;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import lombok.Getter;

/*
Translation values inserted through question.json file
 */
@Getter
public enum Question {

  WHO,
  WHEN,
  WHERE,
  HOW,
  WHY,
  HOW_MANY,
  WHAT,
  WITH_WHO;

  private Word word;

  Question() {
    try {
      File file = new File("src/main/resources/other/questions.json");
      JsonNode[] personalProunouns =
          Config.OBJECT_MAPPER.readValue(file, JsonNode[].class);
      for (JsonNode jsonNode : personalProunouns) {
        Word word = new Word();
        word.setTranslations(Config.OBJECT_MAPPER.readValue(jsonNode.get("translations").toString(), new TypeReference<>() {
        }));
        if (jsonNode.get("id").asText().equals(this.name())) {
          this.word = word;
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static Question getRandomInterrogativeProunoun() {
    return Arrays.stream(values()).skip(RANDOM.nextInt(values().length)).findFirst().get();
  }

}
