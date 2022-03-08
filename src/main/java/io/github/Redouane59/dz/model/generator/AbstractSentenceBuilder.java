package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.function.GeneratorParameters;
import java.util.Optional;

public abstract class AbstractSentenceBuilder {

  public abstract Optional<AbstractSentence> generateRandomSentence(GeneratorParameters bodyArgs);

  public abstract boolean isCompatible(GeneratorParameters bodyArgs);

}
