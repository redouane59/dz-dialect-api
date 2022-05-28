package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.helper.Config;
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

  private final Set<String>         errors = new HashSet<>();
  private       GeneratorParameters bodyArgs;

  public SentenceGenerator(GeneratorParameters bodyArgs) {
    this.bodyArgs = bodyArgs;
  }

  public Sentences generateRandomSentences() {
    Sentences      result       = new Sentences();
    List<Sentence> sentenceList = new ArrayList<>();
    if (bodyArgs.getCount() > Config.MAX_GENERATION_COUNT) {
      errors.add("max count limit (" + Config.MAX_GENERATION_COUNT + ") reached with count=" + bodyArgs.getCount());
      bodyArgs.setCount(Config.MAX_GENERATION_COUNT);
    }
    int i = 0;
    while (sentenceList.size() < bodyArgs.getCount() && i < bodyArgs.getCount() * 5) { // in case no sentence is generated
      Optional<Sentence> sentence = generateRandomSentence(bodyArgs);
      sentence.ifPresent(sentenceList::add);
      i++;
    }
    result.setSentences(sentenceList);
    if (result.getSentences().size() < bodyArgs.getCount() && result.getSentences().size() > 1) {
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
      errors.add(e.getMessage());
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public Optional<SentenceSchema> getRandomSentenceSchema() {
    Set<VerbType> verbTypes = bodyArgs.getVerbsFromIds().stream().map(Verb::getVerbType).collect(Collectors.toSet());
    if (bodyArgs.getSentenceSchemasFromIds().isEmpty()) {
      errors.add("No sentence schema found with the given ids : " + bodyArgs.getSentenceSchemas());
      return Optional.empty();
    }
    List<SentenceSchema> matchingSentenceSchema = bodyArgs.getSentenceSchemasFromIds().stream()
                                                          .filter(o -> o.getVerbType() == null
                                                                       || verbTypes.contains(o.getVerbType()))
                                                          .filter(o -> o.getTenses()
                                                                        .stream()
                                                                        .anyMatch(t -> bodyArgs.getTenses().contains(t)))
                                                          .collect(Collectors.toList());
    if (matchingSentenceSchema.isEmpty()) {
      errors.add("No sentence schema matching with available verb types : " + verbTypes);
      return Optional.empty();
    }
    return Optional.of(matchingSentenceSchema.get(new Random().nextInt(matchingSentenceSchema.size())));
  }

}
