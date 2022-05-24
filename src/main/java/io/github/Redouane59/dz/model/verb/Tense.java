package io.github.Redouane59.dz.model.verb;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// @todo add root tense
public enum Tense {
  PAST(RootTense.PAST),
  PAST2(RootTense.PAST), // @todo dev verbs
  PRESENT(RootTense.PRESENT),
  FUTURE(RootTense.FUTURE),
  IMPERATIVE(RootTense.IMPERATIVE);

  RootTense rootTense;
}
