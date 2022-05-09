package io.github.Redouane59.dz.model;

import static io.github.Redouane59.dz.model.sentence.SentenceBuilder.RANDOM;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Gender {

  M, // masculin
  F, // feminin
  X; // genderless

  public static Gender getRandomGender() {
    return Arrays.stream(values()).skip(RANDOM.nextInt(values().length)).findFirst().get();
  }
}