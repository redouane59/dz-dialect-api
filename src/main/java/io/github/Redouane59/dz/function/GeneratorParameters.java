package io.github.Redouane59.dz.function;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.sentence.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.NVA.NVASentenceBuilder;
import io.github.Redouane59.dz.model.sentence.PVA.PVASentenceBuilder;
import io.github.Redouane59.dz.model.sentence.PVD.PVDSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.PVN.PVNSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.PVO.PVOSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.QVP.QVPSentenceBuilder;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GeneratorParameters {

  @Builder.Default
  private Set<Tense>                              tenses     = Set.of(Tense.PAST, Tense.PRESENT, Tense.FUTURE);
  @Builder.Default
  private Set<String>                             verbs      = DB.VERBS.stream().map(Verb::getId).collect(Collectors.toSet());
  @Builder.Default
  private Set<String>                             nouns      = DB.NOUNS.stream().map(AbstractWord::getId).collect(Collectors.toSet());
  @Builder.Default
  private Set<String>                             adjectives = DB.ADJECTIVES.stream().map(AbstractWord::getId).collect(Collectors.toSet());
  @Builder.Default
  private Set<String>                             adverbs    = DB.ADVERBS.stream().map(Adverb::getId).collect(Collectors.toSet());
  @Builder.Default
  @JsonProperty("word_types")
  private Set<WordType>                           wordTypes  =
      Set.of(WordType.VERB, WordType.ADJECTIVE, WordType.QUESTION, WordType.ADVERB);
  @Builder.Default
  @JsonIgnore
  private List<? extends AbstractSentenceBuilder> generators = List.of(new PVNSentenceBuilder(),
                                                                       new PVASentenceBuilder(),
                                                                       new NVASentenceBuilder(),
                                                                       new PVDSentenceBuilder(),
                                                                       new QVPSentenceBuilder(),
                                                                       new PVOSentenceBuilder());
  @Builder.Default
  @JsonIgnore
  private int                                     count      = 1;

  @JsonIgnore
  public Set<Verb> getVerbsFromIds() {
    return DB.VERBS.stream().filter(o -> verbs.contains(o.getId())).collect(Collectors.toSet());
  }

  @JsonIgnore
  public Set<Noun> getNounsFromIds() {
    return DB.NOUNS.stream().filter(o -> nouns.contains(o.getId())).collect(Collectors.toSet());
  }

  @JsonIgnore
  public Set<Adjective> getAdjectivesFromIds() {
    return DB.ADJECTIVES.stream().filter(o -> adjectives.contains(o.getId())).collect(Collectors.toSet());
  }

  @JsonIgnore
  public Set<Adverb> getAdverbsFromIds() {
    return DB.ADVERBS.stream().filter(o -> adverbs.contains(o.getId())).collect(Collectors.toSet());
  }
}
