package io.github.Redouane59.dz.model;

import static io.github.Redouane59.dz.model.sentence.V2.SentenceBuilder.RANDOM;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Possession {

  I, // related to I, me, mine, our, ours, etc.
  YOU, // related to you, your, yours, etc.
  OTHER; // related to he, she, his, her, their, etc.

  public static Possession getRandomPosession() {
    return Arrays.stream(values()).skip(RANDOM.nextInt(values().length)).findFirst().get();
  }

  public static Possession getRandomPosession(Possession possession, boolean objectOnly) {
    Set<Possession> matchingPossession = Set.of(values());
    if (!objectOnly) {
      matchingPossession = matchingPossession.stream().filter(o -> o != possession || possession == Possession.OTHER).collect(Collectors.toSet());
    } else {
      matchingPossession = matchingPossession.stream().filter(o -> o == Possession.OTHER).collect(Collectors.toSet());
    }
    return matchingPossession.stream().skip(RANDOM.nextInt(matchingPossession.size())).findFirst().get();
  }

}
