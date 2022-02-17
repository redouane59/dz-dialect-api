package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.CustomList;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import java.util.List;
import lombok.Getter;

@Getter
public enum VerbType {

  STATE(CustomList.of(new Translation(Lang.FR, "dans"), new Translation(Lang.DZ, "fel"))),
  ACTION(CustomList.of(new Translation(Lang.FR, "à"), new Translation(Lang.DZ, "lel")));

  private final CustomList<Translation> placePrepositions;

  VerbType(CustomList<Translation> placePropositions) {
    this.placePrepositions = placePropositions;
  }

  public String getPlacePreposition(Lang lang) {
    return this.getPlacePrepositions().stream().filter(o -> o.getLang() == lang).findAny().orElseThrow().getValue();
  }

  public String getPlacePreposition(Lang lang, String nextNoun) {
    List<String> consonant = List.of("d", "n", "r", "t", "s", "z", "ch"); // @todo fix for ch
    if (consonant.contains(nextNoun.substring(0, 1)) && lang == Lang.DZ) {
      return "fe";
    }
    return placePrepositions.getTranslationValue(lang);
  }

}
