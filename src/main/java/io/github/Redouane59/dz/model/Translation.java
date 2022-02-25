package io.github.Redouane59.dz.model;

import java.util.List;
import java.util.Optional;
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


  public static String concatTranslations(Translation... translations) {
    StringBuilder result = new StringBuilder();
    for (Translation translation : translations) {
      result.append(translation.getValue()).append(" ");
    }
    return result.toString();
  }

  public static String printTranslations(List<Translation> translations) {
    Optional<Translation> dzTranslation = translations.stream().filter(o -> o.getLang() == Lang.DZ).findAny();
    Optional<Translation> frTranslation = translations.stream().filter(o -> o.getLang() == Lang.FR).findAny();
    String                dzString      = "";
    String                frString      = "";
    dzString = dzTranslation.orElse(new Translation()).getValue();
    frString = frTranslation.orElse(new Translation()).getValue();
    return (dzString + " -> " + frString).replace("  ", " ");
  }

}