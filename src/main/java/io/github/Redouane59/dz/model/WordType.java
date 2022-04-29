package io.github.Redouane59.dz.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WordType {

  VERB("verb"),
  ADJECTIVE("adjective"),
  NOUN("noun"),
  ADVERB("adverb"),
  QUESTION("question"),
  PRONOUN("pronoun"),
  ARTICLE("article"),
  SUFFIX("suffix"),
  PREPOSITION("preposition"); // à la / au

  private String value;

  public static WordType getWordTypeByValue(String value) {
    return Arrays.stream(WordType.values())
                 .filter(o -> o.getValue().equals(value))
                 .findAny().orElseThrow();
  }
}
