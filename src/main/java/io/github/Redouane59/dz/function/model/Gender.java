package io.github.Redouane59.dz.function.model;

import lombok.Getter;

@Getter
public enum Gender {

  M("le"),
  F("la"),
  X("");

  private final String frArticle;

  Gender(String frArticle) {
    this.frArticle = frArticle;
  }
}