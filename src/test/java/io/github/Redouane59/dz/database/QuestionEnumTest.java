package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.question.Question;
import org.junit.jupiter.api.Test;

public class QuestionEnumTest {

  @Test
  public void test1() {
    Question how = Question.HOW;
    assertEquals(how.getId(), "comment");
    assertEquals(how.getTranslationValue(Lang.FR), "comment est-ce que");
    assertEquals(how.getTranslationValue(Lang.DZ), "kiffech");

    Question combien = Question.HOW_MANY;
    assertEquals(combien.getId(), "combien");
    assertEquals(combien.getTranslationValue(Lang.FR), "combien est-ce que");
    assertEquals(combien.getTranslationValue(Lang.DZ), "ch7al");
  }
}
