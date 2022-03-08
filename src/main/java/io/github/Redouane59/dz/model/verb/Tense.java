package io.github.Redouane59.dz.model.verb;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tense {
  PAST("passé"),
  PRESENT("présent"),
  FUTURE("futur");

  String id;
}
