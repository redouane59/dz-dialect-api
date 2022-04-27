package io.github.Redouane59.dz.sentences;

import io.github.Redouane59.dz.model.sentence.V2.SentenceBuilder;
import io.github.Redouane59.dz.model.word.Sentence;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GenerateSentenceV2Test {

  @Test
  public void testGenericSentenceBuilder() {
    int nbTries = 5;

    List<String> schemaNames = List.of(
        //      "nv_sentence.json",
        "nva_sentence.json"
        //      , "pv_sentence.json"
        //      , "pva_sentence.json"
        //      , "pvn_sentence.json"
        //      , "v_sentence.json"
    );
    System.out.println();
    for (String schema : schemaNames) {
      SentenceBuilder sentenceBuilder = new SentenceBuilder("src/main/resources/sentences/" + schema);
      System.out.println("--- " + schema + " ---");
      for (int i = 0; i < nbTries; i++) {
        Sentence sentence = sentenceBuilder.generate(null).get();
        System.out.println(sentence);
      }
    }
  }
}
