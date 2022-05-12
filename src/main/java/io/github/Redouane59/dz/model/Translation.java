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
    this.arValue = cleanValue(arValue);
  }

  public String cleanValue(String value) {
    if (value == null) {
      return "";
    }
    String newValue = value;
    newValue = newValue.replace("' ", "'").replace("  ", " ");
    // replacing pronouns & articles ending with a vowel when the next word also start by a vowel
    if (lang == Lang.FR) {
      for (char v : Config.VOWELS) {
        newValue = newValue.replace(" je " + v, " j'" + v);
        newValue = newValue.replace(" ce " + v, " c'" + v);
        newValue = newValue.replace(" me " + v, " m'" + v);
        newValue = newValue.replace(" te " + v, " t'" + v);
        newValue = newValue.replace(" le " + v, " l'" + v);
        newValue = newValue.replace(" la " + v, " l'" + v);
        newValue = newValue.replace(" ne " + v, " n'" + v);
        if (newValue.startsWith("le " + v)) {
          newValue = newValue.replace("le " + v, " l'" + v);
        } else if (newValue.startsWith("la " + v)) {
          newValue = newValue.replace("la " + v, " l'" + v);
        } else if (newValue.startsWith("je " + v)) {
          newValue = newValue.replace("je " + v, "j'" + v);
        } else if (newValue.startsWith("ne " + v)) {
          newValue = newValue.replace("ne " + v, "n'" + v);
        }
      }
      newValue = newValue.replace("que il", "qu'il");
      newValue = newValue.replace("que elle", "qu'elle");
      newValue = newValue.replace("que on", "qu'on");
      newValue = newValue.replace("à le", "au");
      newValue = newValue.replace("l' ", "l'");
    } else if (lang == Lang.DZ) {
      // suffixes managed in suffix class
    }
    newValue = newValue.trim();
    return newValue;
  }

}
