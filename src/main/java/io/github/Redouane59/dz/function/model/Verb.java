package io.github.Redouane59.dz.function.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Verb {

  @JsonProperty("fr_value")
  private String           frValue;
  @JsonProperty("conjugators")
  private List<Conjugator> conjugators;

}
