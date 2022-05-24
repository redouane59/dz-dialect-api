package io.github.Redouane59.dz.model.verb;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// @todo add root tense
public enum RootTense {
  PAST("passé"),
  PRESENT("présent"),
  FUTURE("futur"),
  IMPERATIVE("impératif");
  String id;
}
