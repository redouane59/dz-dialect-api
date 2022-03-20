package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.function.GeneratorParameters;
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
    Sentences              result       = new Sentences();
    Set<String>            errors       = new HashSet<>();
    List<AbstractSentence> sentenceList = new ArrayList<>();
    if (bodyArgs.getCount() > MAX_COUNT) {
      result.setErrors(Set.of("max count limit (" + MAX_COUNT + ") reached with count=" + bodyArgs.getCount()));
      bodyArgs.setCount(MAX_COUNT);
    }
    for (int i = 0; i < bodyArgs.getCount(); i++) {
      Optional<AbstractSentence> sentence = generateRandomSentence(bodyArgs);
      if (sentence.isPresent()) {
        sentenceList.add(sentence.get());
      } else {
        errors.add("Some sentences were not generated");
      }
    }
    if (!errors.isEmpty()) {
      result.setErrors(errors);
    }
    result.setSentences(sentenceList);
    result.setCount(sentenceList.size());
    return result;
  }

  public Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs) {
    Optional<AbstractSentenceBuilder> abstractSentenceBuilder = getRandomSentenceBuilder();
    if (abstractSentenceBuilder.isEmpty()) {
      return Optional.empty();
    }
    return abstractSentenceBuilder.get().generateRandomSentence(bodyArgs);
  }

  public Optional<AbstractSentenceBuilder> getRandomSentenceBuilder() {
    List<? extends AbstractSentenceBuilder> matchingGenerators = bodyArgs.getGenerators()
                                                                         .stream().filter(o -> o.isCompatible(bodyArgs)).collect(Collectors.toList());
    if (matchingGenerators.isEmpty()) {
      System.err.println("no generator found");
      return Optional.empty();
    }
    return Optional.of(matchingGenerators.get(new Random().nextInt(matchingGenerators.size())));
  }

}