package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.function.GeneratorParameters;
import java.util.Optional;

public abstract class AbstractSentenceBuilder {

  public abstract Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs);

  /**
   * @return true if the builder is able to return a sentence
   */
  public abstract boolean isCompatible(GeneratorParameters bodyArgs);

}
