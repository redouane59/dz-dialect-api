package io.github.Redouane59.dz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class CustomList<T> extends ArrayList<T> {

  @SafeVarargs
  public static <T> CustomList<T> of(T... elements) {
    CustomList<T> result = new CustomList<>();
    Collections.addAll(result, elements);
    return result;
  }

  public Optional<String> getTranslation(Lang lang) {

    for (T t : this) {
      if (t instanceof Translation) {
        Translation translation = (Translation) t;
        if (translation.getLang() == lang) {
          return Optional.of(translation.getValue());
        }
      } else {
        System.out.println("not translations");
      }
    }
    return Optional.empty();
  }

  public String getTranslationValue(Lang lang) {
    return getTranslation(lang).orElse("");
  }


}
