package io.github.Redouane59.dz.function.model;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AbstractWord {

  private List<Translation> translations;

  public Optional<String> getTranslation(Lang lang) {
    for (Translation translation : translations) {
      if (translation.getLang().equals(lang)) {
        return Optional.of(translation.getValue());
      }
    }
    return Optional.empty();
  }
}
