package io.github.Redouane59.dz.model.word;

import io.github.Redouane59.dz.model.Possession;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PossessiveWord extends GenderedWord {

  private Possession possession;
}
