package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class QuestionTest {

  @Test
  public void testQuestions() {
    Set<AbstractWord> questions = DB.QUESTIONS;
    for (AbstractWord q : questions) {
      assertNotNull(q);
      assertNotNull(q.getValues().get(0).getTranslations());
      assertNotNull(q.getValues().get(0).getTranslationValue(Lang.FR));
      assertNotNull(q.getValues().get(0).getTranslationValue(Lang.DZ));
      System.out.println(q.getValues().get(0).getFrTranslation());
    }
  }
}
