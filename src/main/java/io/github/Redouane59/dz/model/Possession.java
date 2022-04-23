package io.github.Redouane59.dz.model;

import static io.github.Redouane59.dz.model.sentence.WordPicker.RANDOM;

import java.util.Arrays;

public enum Possession {

  I, // related to I, me, mine, our, ours, etc.
  YOU, // related to you, your, yours, etc.
  OTHER; // related to he, she, his, her, their, etc.

  public static Possession getRandomPosession() {
    return Arrays.stream(values()).skip(RANDOM.nextInt(values().length)).findFirst().get();
  }

  public static Possession getRandomPosession(Possession possession) {
    return Arrays.stream(values()).filter(o -> o != possession).skip(RANDOM.nextInt(values().length - 1)).findFirst().get();
  }

}
