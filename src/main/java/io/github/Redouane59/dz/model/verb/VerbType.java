package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import java.util.List;
import lombok.Getter;

@Getter
public enum VerbType {

  // @todo manage en voiture / b
  STATE(List.of(new Translation(Lang.FR, "dans"), new Translation(Lang.DZ, "fel"))),
  DEPLACEMENT(List.of(new Translation(Lang.FR, "à"), new Translation(Lang.DZ, "lel"))),
  ACTION(List.of(new Translation(Lang.FR, ""), new Translation(Lang.DZ, "")));

  private final List<Translation> placePrepositions;

  VerbType(List<Translation> placePropositions) {
    this.placePrepositions = placePropositions;
  }

  private String getPlacePreposition(Lang lang) {
    return this.getPlacePrepositions().stream().filter(o -> o.getLang() == lang).findAny().orElseThrow().getValue();
  }

  public String getPlacePreposition(Lang lang, String nextNoun) {
    if (Config.CONSONANTS.contains(nextNoun.substring(0, 1)) && lang == Lang.DZ) {
      return this.getPlacePreposition(lang).substring(0, 2);
    }
    return getPlacePreposition(lang);
  }

}
