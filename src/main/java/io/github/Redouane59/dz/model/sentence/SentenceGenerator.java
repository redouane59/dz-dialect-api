package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import io.github.Redouane59.dz.model.word.Sentence;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SentenceGenerator {

  private final int                 MAX_COUNT = 30;
  private       GeneratorParameters bodyArgs;

  public SentenceGenerator(GeneratorParameters bodyArgs) {
    this.bodyArgs = bodyArgs;
  }

  public Sentences generateRandomSentences() {
    Sentences      result       = new Sentences();
    Set<String>    errors       = new HashSet<>();
    List<Sentence> sentenceList = new ArrayList<>();
    if (bodyArgs.getCount() > MAX_COUNT) {
      errors.add("max count limit (" + MAX_COUNT + ") reached with count=" + bodyArgs.getCount());
      bodyArgs.setCount(MAX_COUNT);
    }
    for (int i = 0; i < bodyArgs.getCount() * 5; i++) {
      if (sentenceList.size() >= bodyArgs.getCount()) {
        break;
      }
      Optional<Sentence> sentence = generateRandomSentence(bodyArgs);
      sentence.ifPresent(sentenceList::add);
    }
    result.setSentences(sentenceList);
    result.setCount(sentenceList.size());
    if (result.getSentences().size() < bodyArgs.getCount()) {
      errors.add("Some sentences were not generated");
    }
    result.setErrors(errors);
    return result;
  }

  public Optional<Sentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    try {
      Optional<SentenceSchema> sentenceSchema = getRandomSentenceSchema();
      if (sentenceSchema.isEmpty()) {
        return Optional.empty();
      }
      SentenceBuilder sentenceBuilder = new SentenceBuilder(sentenceSchema.get());
      return sentenceBuilder.generate(bodyArgs);
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }


  public Optional<SentenceSchema> getRandomSentenceSchema() {
    Set<VerbType> verbTypes = bodyArgs.getVerbsFromIds().stream().map(Verb::getVerbType).collect(Collectors.toSet());
    if (bodyArgs.getSentenceSchemasFromIds().isEmpty()) {
      System.err.println("no schema found in arguments");
      return Optional.empty();
    }
    List<SentenceSchema> matchingSentenceSchema = bodyArgs.getSentenceSchemasFromIds().stream()
                                                          .filter(o -> o.getVerbType() == null
                                                                       || verbTypes.contains(o.getVerbType()))
                                                          .collect(Collectors.toList());
    if (matchingSentenceSchema.isEmpty()) {
      System.err.println("No sentence schema found with verb types " + verbTypes);
      return Optional.empty();
    }
    return Optional.of(matchingSentenceSchema.get(new Random().nextInt(matchingSentenceSchema.size())));
  }

}
