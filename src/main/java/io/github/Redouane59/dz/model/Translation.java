package io.github.Redouane59.dz.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Translation {

  private Lang   lang;
  private String value;
  @JsonInclude(Include.NON_NULL)
  @JsonProperty("ar_value")
  private String arValue; // translation written in arabic letters

  public Translation(Lang lang, String value) {
    this.lang  = lang;
    this.value = value;
  }

}
