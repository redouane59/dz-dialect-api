package io.github.Redouane59.dz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RootLang {
  FR("français"),
  AR("arabe");

  final String value;
}
