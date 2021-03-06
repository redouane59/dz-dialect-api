package io.github.Redouane59.dz.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.Adjective;
import io.github.Redouane59.dz.model.complement.Noun;
import io.github.Redouane59.dz.model.level.Level;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.verb.RootTense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeneratorParameters {

  @Builder.Default
  private Set<RootTense> tenses              = Set.of(RootTense.PAST, RootTense.PRESENT, RootTense.FUTURE, RootTense.IMPERATIVE);
  @Builder.Default
  private Set<String>    verbs               = DB.VERBS.stream().map(Verb::getId).collect(Collectors.toSet());
  @Builder.Default
  private Set<String>    nouns               = DB.NOUNS.stream().map(AbstractWord::getId).collect(Collectors.toSet());
  @Builder.Default
  private Set<String>    adjectives          = DB.ADJECTIVES.stream().map(AbstractWord::getId).collect(Collectors.toSet());
  @Builder.Default
  private Set<String>    adverbs             = DB.ADVERBS.stream().map(AbstractWord::getId).collect(Collectors.toSet());
  @Builder.Default
  @JsonProperty("word_types")
  private Set<WordType>  wordTypes           = Set.of(WordType.values());
  @Builder.Default
  @JsonIgnore
  private int            count               = 1;
  @Builder.Default
  @JsonProperty("schemas")
  private Set<String>    sentenceSchemas     = DB.SENTENCE_SCHEMAS.stream().map(SentenceSchema::getId).collect(Collectors.toSet());
  @Builder.Default
  @JsonProperty("possible_affirmation")
  private boolean        possibleAffirmation = true;
  @Builder.Default
  @JsonProperty("possible_negation")
  private boolean        possibleNegation    = true;
  @Builder.Default
  private List<Level>    levels              = DB.LEVELS;

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
  public Set<AbstractWord> getAdverbsFromIds() {
    return DB.ADVERBS.stream().filter(o -> adverbs.contains(o.getId())).collect(Collectors.toSet());
  }

  @JsonIgnore
  public Set<SentenceSchema> getSentenceSchemasFromIds() {
    return DB.SENTENCE_SCHEMAS.stream().filter(o -> sentenceSchemas.contains(o.getId())).collect(Collectors.toSet());
  }
}
