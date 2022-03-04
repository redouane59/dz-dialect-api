package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.function.BodyArgs;
import java.util.Optional;

public abstract class AbstractSentenceBuilder {

  public abstract Optional<AbstractSentence> generateRandomSentence(BodyArgs bodyArgs);

  public abstract boolean isCompatible(BodyArgs bodyArgs);

}
