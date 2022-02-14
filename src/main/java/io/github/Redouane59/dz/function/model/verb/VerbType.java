package io.github.Redouane59.dz.function.model.verb;

import io.github.Redouane59.dz.function.model.Lang;
import io.github.Redouane59.dz.function.model.Translation;
import java.util.List;
import lombok.Getter;

@Getter
public enum VerbType {

  STATE(List.of(new Translation(Lang.FR, "dans"), new Translation(Lang.DZ, "fel"))),
  ACTION(List.of(new Translation(Lang.FR, "à"), new Translation(Lang.DZ, "lel")));

  private final List<Translation> placePrepositions;

  VerbType(List<Translation> placePropositions) {
    this.placePrepositions = placePropositions;
  }

  public String getPlacePreposition(Lang lang) {
    return this.getPlacePrepositions().stream().filter(o -> o.getLang() == lang).findAny().orElseThrow().getValue();
  }
}
