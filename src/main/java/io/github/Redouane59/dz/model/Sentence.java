package io.github.Redouane59.dz.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class Sentence extends AbstractWord {

  List<String> verbs = new ArrayList<>();
  @JsonProperty("adjective_ids")
  List<String> adjectiveIds = new ArrayList<>();
  @JsonProperty("noun_ids")
  List<String> nounIds      = new ArrayList<>();

}
