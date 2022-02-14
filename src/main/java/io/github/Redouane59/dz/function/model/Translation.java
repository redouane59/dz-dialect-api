package io.github.Redouane59.dz.function.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Translation {

  @JsonProperty("fr_value")
  private String frValue;
  @JsonProperty("dz_value")
  private String dzValue;
}
