package io.github.Redouane59.dz.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.Getter;

@Getter
public enum InterrogativePronoun {

  WHO(List.of(new Translation(Lang.FR, "qui"), new Translation(Lang.DZ, "chkoun"))),
  WHEN(List.of(new Translation(Lang.FR, "quand"), new Translation(Lang.DZ, "wo9tach"))),
  WHERE(List.of(new Translation(Lang.FR, "où"), new Translation(Lang.DZ, "win"))),
  HOW(List.of(new Translation(Lang.FR, "comment"), new Translation(Lang.DZ, "kiffech"))),
  WHY(List.of(new Translation(Lang.FR, "pourquoi"), new Translation(Lang.DZ, "wa3lech"))),
  HOW_MANY(List.of(new Translation(Lang.FR, "combien"), new Translation(Lang.DZ, "ch3al")));

  private static final List<InterrogativePronoun> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
  private final        List<Translation>          translations;

  InterrogativePronoun(List<Translation> translations) {
    this.translations = translations;
  }

  public static InterrogativePronoun getRandomInterrogativeProunoun() {
    return VALUES.get(new Random().nextInt(VALUES.size()));
  }

  // @todo duplicate
  public String getTranslationValue(Lang lang) {
    return getTranslations()
        .stream().filter(o -> o.getLang() == lang).findAny().get().getValue();
  }

}
