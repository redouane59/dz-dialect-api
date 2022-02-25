package io.github.Redouane59.dz.function;

import static io.github.Redouane59.dz.helper.Config.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.sentence.Sentence;
import io.github.Redouane59.dz.model.sentence.Sentences;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SentencesSerializationTest {

  Sentence  sentence1;
  Sentence  sentence2;
  Sentences sentences;

  public SentencesSerializationTest() {
    List<Translation> translations = new ArrayList<>();
    translations.add(new Translation(Lang.FR, "je suis petit"));
    translations.add(new Translation(Lang.DZ, "rani sghir"));
    sentence1 = new Sentence(translations);
    sentence1.setAdjectiveIds(List.of("petit"));
    sentence1.setVerbIds(List.of("être"));

    translations = new ArrayList<>();
    translations.add(new Translation(Lang.FR, "tu es petite"));
    translations.add(new Translation(Lang.DZ, "raki sghira"));
    sentence2 = new Sentence(translations);
    sentence2.setAdjectiveIds(List.of("petit"));
    sentence2.setVerbIds(List.of("être"));

    List<Sentence> sentenceList = List.of(sentence1, sentence2);
    sentences = new Sentences(sentenceList, 2, null);

  }

  @Test
  public void sentenceSerialization() throws JsonProcessingException {

    String serializedSentence = OBJECT_MAPPER.writeValueAsString(sentence1);
    System.out.println(serializedSentence);
    JsonNode jsonNode = OBJECT_MAPPER.readValue(serializedSentence, JsonNode.class);
    assertTrue(jsonNode.has("verb_ids"));
    assertTrue(jsonNode.has("adjective_ids"));
    assertEquals("je suis petit", jsonNode.get("fr_value").asText());
    assertEquals("rani sghir", jsonNode.get("dz_value").asText());
    assertFalse(jsonNode.has("noun_ids"));
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
