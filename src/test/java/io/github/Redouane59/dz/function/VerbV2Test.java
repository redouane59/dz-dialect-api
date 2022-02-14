package io.github.Redouane59.dz.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Redouane59.dz.function.model.Verb;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class VerbV2Test {

  @Test
  public void testX() throws IOException {

    Verb etre = new ObjectMapper().readValue(new File("src/main/resources/verbs.json"), Verb.class);

    System.out.println(etre);
/*    Adjectivator adjectivator = new Adjectivator(new Translation("petit", "s7ir"),
                                                 new Translation("petite", "s7ira"),
                                                 new Translation("petits", "s7aar"));
    for (conjugation subconjugation : verb.getPastConjugations().getConjugations()) {
      String dzSentence = subconjugation.getTranslation().getDzValue();
      String frSentence = subconjugation.getTranslation().getFrValue();
      Translation
          adjectiveTranslation =
          adjectivator.getAdjectiveByCriterion(subconjugation.getGender(), subconjugation.isSingular()).getTranslation();
      System.out.println(dzSentence + " " + adjectiveTranslation.getDzValue() + " - > " + frSentence + " " + adjectiveTranslation.getFrValue());
    }

    for (Adjective adjective : adjectivator.getAdjectives()) {
      String      dzSentence                = adjective.getTranslation().getDzValue();
      String      frSentence                = adjective.getTranslation().getFrValue();
      Translation subconjugationTranslation = bePast.getConjugationByCriteria(adjective.getGender(), adjective.isSingular()).getTranslation();
      System.out.println(dzSentence
                         + " "
                         + subconjugationTranslation.getDzValue()
                         + " - > "
                         + frSentence
                         + " "
                         + subconjugationTranslation.getFrValue());

    } */
  }


}
