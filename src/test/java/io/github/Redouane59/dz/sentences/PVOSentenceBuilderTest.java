package io.github.Redouane59.dz.sentences;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import io.github.Redouane59.dz.model.generator.PVO.PVOSentence;
import io.github.Redouane59.dz.model.generator.PVO.PVOSentenceBuilder;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class PVOSentenceBuilderTest {


  @Test
  public void generateSentences() {
    int nbTests = 30;
    for (int i = 0; i < nbTests; i++) {
      PVOSentenceBuilder         sentenceBuilder = new PVOSentenceBuilder();
      Optional<AbstractSentence> sentence        = sentenceBuilder.generateRandomSentence(GeneratorParameters.builder().build());
      assertTrue(sentence.isPresent());
      assertNotNull(sentence.get().buildSentenceValue(Lang.DZ));
      assertNotNull(sentence.get().buildSentenceValue(Lang.FR));
      System.out.println(sentence.get());
    }
  }

  @Test
  public void testDemander() {
    PVOSentenceBuilder sentenceBuilder = new PVOSentenceBuilder();
    Verb               demander        = DB.VERBS.stream().filter(x -> x.getId().equals("demander")).findAny().get();
    for (Conjugation conjugation : demander.getConjugators().get(1).getConjugations()) {
      for (PersonalProunoun personalProunoun : PersonalProunoun.values()) {
        if (personalProunoun != PersonalProunoun.UNDEFINED) {
          PVOSentence pvoSentence = new PVOSentence();
          pvoSentence.setVerb(demander);
          pvoSentence.setPersonalProunoun(conjugation.getPersonalPronoun());
          pvoSentence.setSuffixPronoun(personalProunoun);
          pvoSentence.setTense(Tense.PRESENT);
          String fr = pvoSentence.buildSentenceValue(Lang.FR);
          String dz = pvoSentence.buildSentenceValue(Lang.DZ);
          System.out.println(dz + " -> " + fr);
        }
      }


    }

  }
}
