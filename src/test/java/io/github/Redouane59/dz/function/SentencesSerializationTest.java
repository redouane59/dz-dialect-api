package io.github.Redouane59.dz.function;

import static io.github.Redouane59.dz.helper.Config.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.PVA.PVASentence;
import io.github.Redouane59.dz.model.sentence.Sentences;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SentencesSerializationTest {

  AbstractSentence sentence1;
  AbstractSentence sentence2;
  Sentences        sentences;

  public SentencesSerializationTest() {
    List<Translation> translations = new ArrayList<>();
    translations.add(new Translation(Lang.FR, "je suis petit"));
    translations.add(new Translation(Lang.DZ, "rani sghir"));
    sentence1 = new PVASentence();
    sentence1.setTranslations(translations);

    translations = new ArrayList<>();
    translations.add(new Translation(Lang.FR, "tu es petite"));
    translations.add(new Translation(Lang.DZ, "raki sghira"));
    sentence2 = new PVASentence();
    sentence2.setTranslations(translations);

    List<AbstractSentence> sentenceList = List.of(sentence1, sentence2);
    sentences = new Sentences(sentenceList, 2, null);

  }

  @Test
  public void sentenceSerialization() throws JsonProcessingException {

    String serializedSentence = OBJECT_MAPPER.writeValueAsString(sentence1);
    System.out.println(serializedSentence);
    JsonNode jsonNode = OBJECT_MAPPER.readValue(serializedSentence, JsonNode.class);
    assertEquals("je suis petit", jsonNode.get("fr_value").asText());
    assertEquals("rani sghir", jsonNode.get("dz_value").asText());
  }

  @Test
  public void sentencesSerialization() throws JsonProcessingException {
    String serializedSentences = OBJECT_MAPPER.writeValueAsString(sentences);
    System.out.println(serializedSentences);
    JsonNode jsonNode = OBJECT_MAPPER.readValue(serializedSentences, JsonNode.class);
    assertEquals(2, jsonNode.get("count").asInt());
    assertEquals(2, jsonNode.get("sentences").size());
  }

}
