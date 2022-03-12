package io.github.Redouane59.dz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Lang {
  FR(RootLang.FR, "Français (France)"),
  DZ(RootLang.AR, "Dardja (Alger)");
  // add other dialects here

  final RootLang rootLang;
  final String   value;
}
