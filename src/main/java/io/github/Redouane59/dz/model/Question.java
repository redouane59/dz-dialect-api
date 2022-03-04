package io.github.Redouane59.dz.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.Getter;

@Getter
public enum Question {

  WHO(List.of(new Translation(Lang.FR, "qui"), new Translation(Lang.DZ, "chkoun"))),
  WHEN(List.of(new Translation(Lang.FR, "quand est-ce que"), new Translation(Lang.DZ, "wo9tach"))),
  WHERE(List.of(new Translation(Lang.FR, "où est-ce que"), new Translation(Lang.DZ, "win"))),
  HOW(List.of(new Translation(Lang.FR, "comment est-ce que"), new Translation(Lang.DZ, "kiffech"))),
  WHY(List.of(new Translation(Lang.FR, "pourquoi est-ce que"), new Translation(Lang.DZ, "wa3lech"))),
  HOW_MANY(List.of(new Translation(Lang.FR, "combien est-ce que"), new Translation(Lang.DZ, "ch3al"))),
  WHAT(List.of(new Translation(Lang.FR, "qu'est-ce que"), new Translation(Lang.DZ, "wesh")));

  private static final List<Question>    VALUES = Collections.unmodifiableList(Arrays.asList(values()));
  private final        List<Translation> translations;

  Question(List<Translation> translations) {
    this.translations = translations;
  }

  public static Question getRandomInterrogativeProunoun() {
    return VALUES.get(new Random().nextInt(VALUES.size()));
  }

  // @todo duplicate
  public String getTranslationValue(Lang lang) {
    return getTranslations()
        .stream().filter(o -> o.getLang() == lang).findAny().get().getValue();
  }

}
