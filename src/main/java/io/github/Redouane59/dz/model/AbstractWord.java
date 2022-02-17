package io.github.Redouane59.dz.model;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AbstractWord {

  private CustomList<Translation> translations = new CustomList<>();

  public Optional<String> getTranslation(Lang lang) {
    for (Translation translation : translations) {
      if (translation.getLang().equals(lang)) {
        return Optional.of(translation.getValue());
      }
    }
    return Optional.empty();
  }
}
