package io.github.Redouane59.dz.function;

import io.github.Redouane59.dz.model.sentence.Sentences;
import io.github.Redouane59.dz.model.word.Sentence;

public class SentencesSerializationTest {

  Sentence  sentence1;
  Sentence  sentence2;
  Sentences sentences;

 /* public SentencesSerializationTest() {
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
  }*/

}
