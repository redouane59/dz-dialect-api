package io.github.Redouane59.dz.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BodyArgs {

  @Builder.Default
  private List<Tense>    tenses     = List.of(Tense.PAST, Tense.PRESENT, Tense.FUTURE);
  @Builder.Default
  private List<String>   verbs      = DB.VERBS.stream().map(Verb::getId).collect(Collectors.toList());
  @Builder.Default
  private List<String>   nouns      = DB.NOUNS.stream().map(AbstractWord::getId).collect(Collectors.toList());
  @Builder.Default
  private List<String>   adjectives = DB.ADJECTIVES.stream().map(AbstractWord::getId).collect(Collectors.toList());
  @Builder.Default
  @JsonProperty("word_types")
  private List<WordType> wordTypes  = List.of(WordType.PLACE, WordType.VERB, WordType.ADJECTIVE, WordType.QUESTION);
  @Builder.Default
  private int            count      = 1;
}
