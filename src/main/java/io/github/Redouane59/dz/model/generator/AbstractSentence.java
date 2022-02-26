package io.github.Redouane59.dz.model.generator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.word.Word;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractSentence extends Word {

  //public static abstract AbstractSentence generateRandomSentence(BodyArgs bodyArgs);

  @JsonIgnore
  public abstract String buildSentenceValue(Lang lang);

  @JsonIgnore
  public String cleanResponse(String result) {
    String newResult = result;
    // replacing je + vowel with j'+vowel
    for (char c : Config.VOWELS) {
      newResult = newResult.replace("je " + c, "j'" + c);
    }
    newResult = newResult.replace("l' ", "l'");
    return newResult;
  }
}
