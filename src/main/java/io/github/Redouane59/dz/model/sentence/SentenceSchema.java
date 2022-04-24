package io.github.Redouane59.dz.model.sentence;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SentenceSchema {

  private String         id;
  @JsonProperty("fr_sequence")
  private List<WordType> frSequence;
  @JsonProperty("ar_sequence")
  private List<WordType> arSequence;
  private List<Tense>    tenses;
  @JsonProperty("subject_position")
  private int            subjectPosition;
  @JsonProperty("verb_type")
  private VerbType       verbType;

}
