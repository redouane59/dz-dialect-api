package io.github.Redouane59.dz.model.level;

import io.github.Redouane59.dz.function.GeneratorParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Level {

  private int                 id;
  private String              description;
  private GeneratorParameters parameters;
}
