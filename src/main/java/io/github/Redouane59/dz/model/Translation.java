package io.github.Redouane59.dz.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.helper.Config;
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
  private Gender gender; // only used for nouns that have different gender in FR/DZ
  @JsonInclude(Include.NON_NULL)
  @JsonProperty("ar_value")
  private String arValue; // translation written in arabic letters

  public Translation(Lang lang, String value) {
    this.lang  = lang;
    this.value = cleanValue(value);
  }

  public Translation(Lang lang, String value, String arValue) {
    this(lang, value);
    this.arValue = arValue;
  }

  public String cleanValue(String value) {
    if (value == null) {
      return "";
    }
    String newValue = value;
    // replacing pronouns & articles ending with a vowel when the next word also start by a vowel
    if (lang == Lang.FR) {
      for (char c : Config.VOWELS) {
        newValue = newValue.replace(" je " + c, "j'" + c);
        newValue = newValue.replace(" ce " + c, "c'" + c);
        newValue = newValue.replace(" me " + c, " m'" + c);
        newValue = newValue.replace(" te " + c, " t'" + c);
        newValue = newValue.replace(" le " + c, " l'" + c);
        newValue = newValue.replace(" la " + c, " l'" + c);
        if (newValue.startsWith("le " + c)) {
          newValue = newValue.replace("le " + c, " l'" + c);
        } else if (newValue.startsWith("la " + c)) {
          newValue = newValue.replace("la " + c, " l'" + c);
        } else if (newValue.startsWith("je " + c)) {
          newValue = newValue.replace("je " + c, "j'" + c);
        }
      }
      newValue = newValue.replace("que il", "qu'il");
      newValue = newValue.replace("que elle", "qu'elle");
      newValue = newValue.replace("que on", "qu'on");
      newValue = newValue.replace("à le", "au");
      newValue = newValue.replace("l' ", "l'");
    }
    // @todo add iou -> ih
    return newValue;
  }

}
