package io.github.Redouane59.dz.model.sentence;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.ArrayList;
import java.util.LinkedList;
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
  private List<WordType> frSequence = new LinkedList<>();
  @JsonProperty("ar_sequence")
  private List<WordType> arSequence = new LinkedList<>();
  private List<Tense>    tenses     = new ArrayList<>();
  @JsonProperty("subject_position")
  private int            subjectPosition;
  @JsonProperty("verb_type")
  private VerbType       verbType;
  @JsonProperty("noun_types")
  private List<NounType> nounTypes  = new ArrayList<>();
  @JsonProperty("possible_negation")
  private boolean        possibleNegation;
  @JsonProperty("definitive_adjective")
  private boolean        definitiveAdjective; // when pronoun replace verb, ex: ana kbir

}
