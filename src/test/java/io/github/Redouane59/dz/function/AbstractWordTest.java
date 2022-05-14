package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import org.junit.jupiter.api.Test;

public class AbstractWordTest {

  AbstractWord abstractWord;

  public AbstractWordTest() {
    abstractWord = new AbstractWord();
    abstractWord.setId("grand");
    abstractWord.setWordType(WordType.ADJECTIVE);
    Conjugation grand = new Conjugation();
    grand.setGender(Gender.M);
    grand.setSingular(true);
    grand.addFrTranslation("grand");
    grand.addDzTranslation("kbir", "*");
    abstractWord.getValues().add(grand);
    Conjugation grande = new Conjugation();
    grande.setGender(Gender.F);
    grande.setSingular(true);
    grande.addFrTranslation("grande");
    grande.addDzTranslation("kbira", "*");
    abstractWord.getValues().add(grande);
    Conjugation grands = new Conjugation();
    grands.addFrTranslation("grands");
    grands.addDzTranslation("kbar", "*");
    grands.setGender(Gender.X);
    grands.setSingular(false);
    abstractWord.getValues().add(grands);
  }

  @Test
  public void testMSword() {
    assertEquals("grand", abstractWord.getTranslationValueByGender(Gender.M, true, Lang.FR));
    assertEquals("kbir", abstractWord.getTranslationValueByGender(Gender.M, true, Lang.DZ));
  }

  @Test
  public void testFSword() {
    assertEquals("grande", abstractWord.getTranslationValueByGender(Gender.F, true, Lang.FR));
    assertEquals("kbira", abstractWord.getTranslationValueByGender(Gender.F, true, Lang.DZ));
  }

  @Test
  public void testPword() {
    assertEquals("grands", abstractWord.getTranslationValueByGender(Gender.X, false, Lang.FR));
    assertEquals("kbar", abstractWord.getTranslationValueByGender(Gender.X, false, Lang.DZ));
  }
}
