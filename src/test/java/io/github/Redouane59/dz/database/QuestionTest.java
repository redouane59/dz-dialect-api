package io.github.Redouane59.dz.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.question.Question;
import java.util.List;
import org.junit.jupiter.api.Test;

public class QuestionTest {

  @Test
  public void testQuestions() {
    List<Question> questions = List.of(Question.values());
    for (Question q : questions) {
      assertNotNull(q);
      assertNotNull(q.getWord().getTranslations());
      assertNotNull(q.getWord().getTranslationValue(Lang.FR));
      assertNotNull(q.getWord().getTranslationValue(Lang.DZ));
      System.out.println(q.getWord().getFrTranslation());
    }
  }
}
