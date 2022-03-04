package io.github.Redouane59.dz.model.adverb;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.word.Word;
import lombok.Getter;

@Getter
public class Adverb extends Word {

  private String   id;
  @JsonProperty("word_type")
  private WordType wordType;
}
