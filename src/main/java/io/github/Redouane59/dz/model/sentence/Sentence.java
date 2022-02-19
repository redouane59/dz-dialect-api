package io.github.Redouane59.dz.model.sentence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.word.Word;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sentence extends Word {

  @JsonInclude(Include.NON_NULL)
  @JsonProperty("verb_ids")
  List<String> verbIds;
  @JsonProperty("adjective_ids")
  @JsonInclude(Include.NON_NULL)
  List<String> adjectiveIds;
  @JsonProperty("noun_ids")
  @JsonInclude(Include.NON_NULL)
  List<String> nounIds;

  public Sentence(List<Translation> translations) {
    super(translations);
  }

}
