package io.github.Redouane59.dz.function;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.adjective.AdjectiveRoot;
import io.github.Redouane59.dz.model.noun.NounRoot;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BodyArgs {

  @Builder.Default
  private List<Tense>  tenses     = List.of(Tense.PAST, Tense.PRESENT, Tense.FUTUR);
  @Builder.Default
  private List<String> verbs      = DB.VERBS.stream().map(Verb::getId).collect(Collectors.toList());
  @Builder.Default
  private List<String> nouns      = DB.NOUNS.stream().map(NounRoot::getId).collect(Collectors.toList());
  @Builder.Default
  private List<String> adjectives = DB.ADJECTIVES.stream().map(AdjectiveRoot::getId).collect(Collectors.toList());
  @Builder.Default
  private int          count      = 1;

/*
  public BodyArgs() {
    tenses = List.of(Tense.PAST, Tense.PRESENT, Tense.FUTUR);
    verbs  = DB.VERBS.stream().map(Verb::getId).collect(Collectors.toList());
    nouns  = DB.NOUNS.stream().map(NounRoot::getId).collect(Collectors.toList());
    DB.ADJECTIVES.stream().map(AdjectiveRoot::getId).collect(Collectors.toList());
  } */

}
