package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonalProunoun {
  I("1XS", Possession.I, true, Gender.X,
    List.of(new Translation(Lang.FR, "je"), new Translation(Lang.DZ, "ana"))),
  YOU_M("2MS", Possession.YOU, true, Gender.M,
        List.of(new Translation(Lang.FR, "tu"), new Translation(Lang.DZ, "enta"))),
  YOU_F("2FS", Possession.YOU, true, Gender.F,
        List.of(new Translation(Lang.FR, "tu"), new Translation(Lang.DZ, "enti"))),
  HE("3MS", Possession.OTHER, true, Gender.M,
     List.of(new Translation(Lang.FR, "il"), new Translation(Lang.DZ, "houwa"))),
  SHE("3FS", Possession.OTHER, true, Gender.F,
      List.of(new Translation(Lang.FR, "elle"), new Translation(Lang.DZ, "hiya"))),
  WE("1XP", Possession.I, false, Gender.X,
     List.of(new Translation(Lang.FR, "on"), new Translation(Lang.DZ, "7na"))),
  YOU_P("2XP", Possession.YOU, false, Gender.X,
        List.of(new Translation(Lang.FR, "vous"), new Translation(Lang.DZ, "ntouma"))),
  THEY_M("3MP", Possession.OTHER, false, Gender.M,
         List.of(new Translation(Lang.FR, "ils"), new Translation(Lang.DZ, "houma"))),
  THEY_F("3FP", Possession.OTHER, false, Gender.F,
         List.of(new Translation(Lang.FR, "elles"), new Translation(Lang.DZ, "houma"))),
  UNDEFINED("undefined", Possession.OTHER, true, Gender.X, new ArrayList<>());

  private final String            id;
  private final Possession        possession;
  private final boolean           singular;
  private final Gender            gender;
  private final List<Translation> translations;


  public static PersonalProunoun getRandomPersonalPronoun() {
    return PersonalProunoun.values()[new Random().nextInt(PersonalProunoun.values().length - 1)];
  }

  public PersonalProunoun getRandomDifferentPersonalPronoun() {
    List<PersonalProunoun> matchingPronouns = Collections.unmodifiableList(Arrays.asList(values()))
                                                         .stream()
                                                         .filter(o -> o != PersonalProunoun.UNDEFINED)
                                                         .filter(o -> o.getPossession() != this.getPossession())
                                                         .collect(Collectors.toList());
    return matchingPronouns.get(new Random().nextInt(matchingPronouns.size()));
  }

  public String getTranslationValue(Lang lang) {
    return translations.stream().filter(o -> o.getLang() == lang).findAny().get().getValue();
  }


}
