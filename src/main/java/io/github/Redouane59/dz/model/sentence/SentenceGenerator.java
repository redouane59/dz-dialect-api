package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.sentence.V2.SentenceBuilder;
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
      result.setErrors(Set.of("max count limit (" + MAX_COUNT + ") reached with count=" + bodyArgs.getCount()));
      bodyArgs.setCount(MAX_COUNT);
    }
    for (int i = 0; i < bodyArgs.getCount() * 2; i++) {
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
      Optional<SentenceBuilder> abstractSentenceBuilder = getRandomSentenceBuilder();
      if (abstractSentenceBuilder.isEmpty()) {
        return Optional.empty();
      }
      return abstractSentenceBuilder.get().generate(bodyArgs);
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public Optional<SentenceBuilder> getRandomSentenceBuilder() {
    // @todo add criterion to avoid non matching sentenceBuilder
    List<SentenceBuilder> matchingGenerators = bodyArgs.getGenerators()
                                                       .stream()
                                                       .filter(o -> o.isCompatible(bodyArgs))
                                                       .collect(Collectors.toList());
    if (matchingGenerators.isEmpty()) {
      System.err.println("no generator found");
      return Optional.empty();
    }
    return Optional.of(matchingGenerators.get(new Random().nextInt(matchingGenerators.size())));
  }

}
