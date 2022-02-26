package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.model.generator.PV.PVA.PVASentenceBuilder;
import io.github.Redouane59.dz.model.generator.PV.PVN.PVNSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.Sentences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SentenceGenerator {

  private final int                                     MAX_COUNT  = 30;
  private       BodyArgs                                bodyArgs   = BodyArgs.builder().build();
  private       List<? extends AbstractSentenceBuilder> generators = List.of(
      new PVNSentenceBuilder(), new PVASentenceBuilder()
  );

  public SentenceGenerator(BodyArgs bodyArgs) {
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
      sentenceList.add(generateRandomSentence(bodyArgs));
    }
    if (!errors.isEmpty()) {
      result.setErrors(errors);
    }
    result.setSentences(sentenceList);
    result.setCount(sentenceList.size());
    return result;
  }

  public AbstractSentence generateRandomSentence(BodyArgs bodyArgs) {
    return generators.get(new Random().nextInt(generators.size())).generateRandomSentence(bodyArgs);
  }

}
