package io.github.Redouane59.dz.database;

import static io.github.Redouane59.dz.model.sentence.WordPicker.RANDOM;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.verb.PersonalPronouns;
import io.github.Redouane59.dz.model.verb.PersonalPronouns.PersonalPronoun;
import org.junit.jupiter.api.Test;

public class PersonalPronounTest {

  @Test
  public void testPronouns() {
    PersonalPronouns pronouns = DB.PERSONAL_PRONOUNS;
    assertNotNull(pronouns);
    PersonalPronoun
        pronoun =
        PersonalPronouns.getPersonalPronouns().stream().skip(RANDOM.nextInt(PersonalPronouns.getPersonalPronouns().size())).findFirst().get();
    assertNotNull(pronoun.getGender());
    assertNotNull(pronoun.getPossession());
    assertNotNull(pronoun.getTranslations());
    assertNotNull(pronoun.getTranslationValue(Lang.FR));
    assertNotNull(pronoun.getTranslationValue(Lang.DZ));
  }
}
