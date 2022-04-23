package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.function.GeneratorParameters;
import java.util.Optional;

public interface ISentenceBuilder {

  Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs);

  /**
   * @return true if the builder is able to return a sentence
   */
  boolean isCompatible(GeneratorParameters bodyArgs);

}
