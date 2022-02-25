package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.sentence.Sentence;
import io.github.Redouane59.dz.model.sentence.Sentences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SentenceGenerator {

  private final int                                       MAX_COUNT  = 30;
  private       BodyArgs                                  bodyArgs   = BodyArgs.builder().build();
  private       List<? extends AbstractSentenceGenerator> generators = List.of(
      new NounVerbAdjectiveGenerator() //,
      //   new PronounVerbAdjectiveGenerator(),
      //   new PronounVerbPlaceGenerator(),
      //  new QuestionVerbSubjectGenerator()
  );

  public SentenceGenerator(BodyArgs bodyArgs) {
    this.bodyArgs = bodyArgs;
  }

  public Sentences generateRandomSentences() {
    Sentences      result       = new Sentences();
    Set<String>    errors       = new HashSet<>();
    List<Sentence> sentenceList = new ArrayList<>();
    if (bodyArgs.getCount() > MAX_COUNT) {
      System.err.println("max count limit reached with : " + bodyArgs.getCount());
      bodyArgs.setCount(MAX_COUNT);
    }
    for (int i = 0; i < bodyArgs.getCount(); i++) {
      Optional<Sentence> sentence = generateRandomSentence(bodyArgs);
      if (sentence.isPresent()) {
        sentenceList.add(sentence.get());
      } else {
        errors.add("No sentence generated because nothing was found matching input criterion");
      }
    }
    if (!errors.isEmpty()) {
      result.setErrors(errors);
    }
    result.setSentences(sentenceList);
    result.setCount(sentenceList.size());
    return result;
  }

  public Optional<Sentence> generateRandomSentence(BodyArgs bodyArgs) {
    AbstractSentenceGenerator generator = generators.get(new Random().nextInt(generators.size()));
    if (bodyArgs.getWordTypes().contains(WordType.VERB)) {
      return generator.generateSentence();
    }
    return Optional.empty();
  }

}
