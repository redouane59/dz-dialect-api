package io.github.Redouane59.dz.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.word.AbstractWord;
import org.junit.jupiter.api.Test;

public class AbstractWordTest {

  @Test
  public void getPersonalProunounTest() {
    assertEquals(Gender.M, AbstractWord.getPersonalPronoun(Gender.M, true, Possession.I).get().getGender());
    assertTrue(AbstractWord.getPersonalPronoun(Gender.M, true, Possession.I).get().isSingular());
    assertEquals(Possession.I, AbstractWord.getPersonalPronoun(Gender.M, true, Possession.I).get().getPossession());

    assertEquals(Gender.F, AbstractWord.getPersonalPronoun(Gender.F, true, Possession.I).get().getGender());
    assertTrue(AbstractWord.getPersonalPronoun(Gender.F, true, Possession.I).get().isSingular());
    assertEquals(Possession.I, AbstractWord.getPersonalPronoun(Gender.F, true, Possession.I).get().getPossession());

    assertEquals(Gender.M, AbstractWord.getPersonalPronoun(Gender.M, true, Possession.YOU).get().getGender());
    assertTrue(AbstractWord.getPersonalPronoun(Gender.M, true, Possession.YOU).get().isSingular());
    assertEquals(Possession.YOU, AbstractWord.getPersonalPronoun(Gender.M, true, Possession.YOU).get().getPossession());

    assertEquals(Gender.F, AbstractWord.getPersonalPronoun(Gender.F, true, Possession.YOU).get().getGender());
    assertTrue(AbstractWord.getPersonalPronoun(Gender.F, true, Possession.YOU).get().isSingular());
    assertEquals(Possession.YOU, AbstractWord.getPersonalPronoun(Gender.F, true, Possession.YOU).get().getPossession());

    assertEquals(Gender.M, AbstractWord.getPersonalPronoun(Gender.M, true, Possession.OTHER).get().getGender());
    assertTrue(AbstractWord.getPersonalPronoun(Gender.M, true, Possession.OTHER).get().isSingular());
    assertEquals(Possession.OTHER, AbstractWord.getPersonalPronoun(Gender.M, true, Possession.OTHER).get().getPossession());

    assertEquals(Gender.F, AbstractWord.getPersonalPronoun(Gender.F, true, Possession.OTHER).get().getGender());
    assertTrue(AbstractWord.getPersonalPronoun(Gender.F, true, Possession.OTHER).get().isSingular());
    assertEquals(Possession.OTHER, AbstractWord.getPersonalPronoun(Gender.F, true, Possession.OTHER).get().getPossession());

    assertEquals(Gender.M, AbstractWord.getPersonalPronoun(Gender.M, false, Possession.I).get().getGender());
    assertFalse(AbstractWord.getPersonalPronoun(Gender.M, false, Possession.I).get().isSingular());
    assertEquals(Possession.I, AbstractWord.getPersonalPronoun(Gender.M, false, Possession.I).get().getPossession());

    assertEquals(Gender.M, AbstractWord.getPersonalPronoun(Gender.M, false, Possession.YOU).get().getGender());
    assertFalse(AbstractWord.getPersonalPronoun(Gender.M, false, Possession.YOU).get().isSingular());
    assertEquals(Possession.YOU, AbstractWord.getPersonalPronoun(Gender.M, false, Possession.YOU).get().getPossession());

    assertEquals(Gender.M, AbstractWord.getPersonalPronoun(Gender.M, true, Possession.OTHER).get().getGender());
    assertTrue(AbstractWord.getPersonalPronoun(Gender.M, true, Possession.OTHER).get().isSingular());
    assertEquals(Possession.OTHER, AbstractWord.getPersonalPronoun(Gender.M, true, Possession.OTHER).get().getPossession());

    assertEquals(Gender.F, AbstractWord.getPersonalPronoun(Gender.F, true, Possession.OTHER).get().getGender());
    assertTrue(AbstractWord.getPersonalPronoun(Gender.F, true, Possession.OTHER).get().isSingular());
    assertEquals(Possession.OTHER, AbstractWord.getPersonalPronoun(Gender.F, true, Possession.OTHER).get().getPossession());

  }

  @Test
  public void getRandomImperativePronounTest() {
    for (int i = 0; i < 10; i++) {
      assertEquals(Possession.YOU, AbstractWord.getRandomImperativePersonalPronoun().getPossession());
    }
  }
}
