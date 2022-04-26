package io.github.Redouane59.dz.sentences;

import io.github.Redouane59.dz.model.sentence.V2.SentenceBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GenerateSentenceV2Test {

  @Test
  public void testGenericSentenceBuilder() {
    int nbTries = 25;

    List<String> schemaNames = List.of(
        //   "nv_sentence.json",
        "nva_sentence.json"
        //   ,"pv_sentence.json",
        //   "pva_sentence.json",
        //   "pvn_sentence.json"
        //   , "v_sentence.json"
    );
    // List<String> schemaNames = List.of("pvn_sentence.json");
    System.out.println();
    for (String schema : schemaNames) {
      SentenceBuilder sentenceBuilder = new SentenceBuilder("src/main/resources/sentences/" + schema);
      System.out.println("--- " + schema + " ---");
      for (int i = 0; i < nbTries; i++) {
        System.out.println(sentenceBuilder.generateFrTranslation().getValue());
        System.out.println(sentenceBuilder.generateArTranslation().getValue());
      }
    }
  }
}
