package io.github.Redouane59.dz.model.question;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// @todo use json instead of translation
public enum Question {

  WHO("qui", List.of(new Translation(Lang.FR, "qui"), new Translation(Lang.DZ, "chkoun"))),
  WHEN("quand", List.of(new Translation(Lang.FR, "quand est-ce que"), new Translation(Lang.DZ, "wo9tach"))),
  WHERE("où", List.of(new Translation(Lang.FR, "où est-ce que"), new Translation(Lang.DZ, "win"))),
  HOW("comment", List.of(new Translation(Lang.FR, "comment est-ce que"), new Translation(Lang.DZ, "kiffech"))),
  WHY("pourquoi", List.of(new Translation(Lang.FR, "pourquoi est-ce que"), new Translation(Lang.DZ, "wa3lech"))),
  HOW_MANY("combien", List.of(new Translation(Lang.FR, "combien est-ce que"), new Translation(Lang.DZ, "ch7al"))),
  WHAT("quoi", List.of(new Translation(Lang.FR, "qu'est-ce que"), new Translation(Lang.DZ, "wesh")));

  private static final List<Question>    VALUES = Collections.unmodifiableList(Arrays.asList(values()));
  private final        String            id;
  private final        List<Translation> translations;


  public static Question getRandomInterrogativeProunoun() {
    return VALUES.get(new Random().nextInt(VALUES.size()));
  }

  // @todo duplicate
  public String getTranslationValue(Lang lang) {
    return getTranslations()
        .stream().filter(o -> o.getLang() == lang).findAny().get().getValue();
  }

}
