package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class WordPicker {

  public static Random RANDOM = new Random();

  public static Optional<Verb> pickRandomVerb(List<Verb> verbs) {
    if (verbs.isEmpty()) {
      System.err.println("adjectives list empty");
    }
    return Optional.of(verbs.get(RANDOM.nextInt(verbs.size())));
  }

  public static Optional<Verb> pickRandomVerb(List<Verb> verbs, Tense tense) {
    return verbs.stream().filter(o -> o.getConjugators().stream().anyMatch(a -> a.getTense() == tense)).findAny();
  }

  public static Optional<Verb> pickRandomVerb(List<Verb> verbs, Tense tense, VerbType verbType) {
    List<Verb> matchingVerbs = verbs.stream()
                                    .filter(o -> o.getVerbType() == verbType)
                                    .filter(o -> o.getConjugators().stream().anyMatch(a -> a.getTense() == tense))
                                    .collect(Collectors.toList());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingVerbs.get(RANDOM.nextInt(matchingVerbs.size())));
  }

  public static Optional<Verb> pickRandomVerb(List<Verb> verbs, VerbType verbType) {
    List<Verb> matchingVerbs = verbs.stream().filter(o -> o.getVerbType() == verbType).collect(Collectors.toList());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingVerbs.get(RANDOM.nextInt(matchingVerbs.size())));
  }

  public static Optional<Conjugator> pickRandomConjugator(List<Verb> verbs, List<Tense> tenses) {
    Optional<Verb> verb = pickRandomVerb(verbs);
    if (verb.isEmpty()) {
      return Optional.empty();
    }
    return verb.get().getRandomConjugator(tenses);
  }

  public static Optional<Conjugator> pickRandomConjugator(List<Verb> verbs, List<Tense> tenses, VerbType verbType) {
    Optional<Verb> verb = pickRandomVerb(verbs, verbType);
    if (verb.isEmpty()) {
      return Optional.empty();
    }
    return verb.get().getRandomConjugator(tenses);
  }

  public static Optional<Adjective> pickRandomAdjective(final List<Adjective> adjectives) {
    if (adjectives.isEmpty()) {
      System.err.println("adjectives list empty");
      return Optional.empty();
    }
    return Optional.of(adjectives.get(RANDOM.nextInt(adjectives.size())));
  }

  public static Optional<Adjective> pickRandomAdjective(final List<Adjective> adjectives, WordType wordType) {
    List<Adjective> matchingAdjectives = adjectives.stream().filter(o -> o.getPossibleNouns().contains(wordType)).collect(Collectors.toList());
    if (matchingAdjectives.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingAdjectives.get(RANDOM.nextInt(matchingAdjectives.size())));
  }

  public static Optional<Noun> pickRandomNoun(final List<Noun> nouns) {
    if (nouns.isEmpty()) {
      System.err.println("nouns list empty");
      return Optional.empty();
    }
    return Optional.of(nouns.get(RANDOM.nextInt(nouns.size())));
  }

  public static Optional<Noun> pickRandomNoun(final List<Noun> nouns, WordType wordType) {
    List<Noun> matchingNouns = nouns.stream().filter(o -> o.getWordType() == wordType).collect(Collectors.toList());
    if (matchingNouns.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingNouns.get(RANDOM.nextInt(matchingNouns.size())));
  }

  public static Tense getRandomTense(List<Tense> tenses) {
    return tenses.get(RANDOM.nextInt(tenses.size()));
  }

  public static Tense getRandomTense() {
    int randomNb = new Random().nextInt(3);
    switch (randomNb) {
      case 0:
        return Tense.PAST;
      case 1:
        return Tense.PRESENT;
      case 2:
        return Tense.FUTURE;
      default:
        return Tense.PRESENT;
    }
  }

}
