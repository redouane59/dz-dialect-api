package io.github.Redouane59.dz.model.adjective;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.Gender;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdjectiveRoot {

  List<Adjective> adjectives = new ArrayList<>();
  @JsonProperty("id")
  private String id;

  // @todo to remove
  /*public AdjectiveRoot(Translation m, Translation f, Translation p) {
    adjectives.add(new Adjective(Gender.M, true, m));
    adjectives.add(new Adjective(Gender.F, true, f));
    adjectives.add(new Adjective(Gender.X, false, p));

  } */

  public Adjective getAdjective(Gender gender, boolean singular) {
    Adjective result = new Adjective();
    for (Adjective adjective : adjectives) {
      if (adjective.isSingular() == singular && (adjective.getGender() == gender || gender == Gender.X)) {
        return adjective;
      }
    }
    return result;
  }
}
