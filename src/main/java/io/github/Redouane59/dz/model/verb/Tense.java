package io.github.Redouane59.dz.model.verb;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tense {
  PAST("passé"),
  PAST2("passé2"), // @todo dev verbs
  PRESENT("présent"),
  FUTURE("futur"),
  IMPERATIVE("impératif");

  String id;
}
