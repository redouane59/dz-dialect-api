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
  public static String cleanResponse(String result) {
    String newResult = result;
    // replacing je + vowel with j'+vowel
    for (char c : Config.VOWELS) {
      newResult = newResult.replace("je " + c, "j'" + c);
      newResult = newResult.replace("ce " + c, "c'" + c);
      newResult = newResult.replace("que il", "qu'il");
      newResult = newResult.replace("que elle", "qu'elle");
    }
    newResult = newResult.replace("à le", "au");
    newResult = newResult.replace("l' ", "l'");
    return newResult;
  }

  @JsonIgnore
  public abstract String buildSentenceValue(Lang lang);
}
