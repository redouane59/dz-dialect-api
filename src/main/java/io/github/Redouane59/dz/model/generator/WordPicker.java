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

  public static Verb pickRandomVerb(List<Verb> verbs) {
    return verbs.get(RANDOM.nextInt(verbs.size()));
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
    Verb verb = pickRandomVerb(verbs);
    return verb.getRandomConjugator(tenses);
  }

  public static Optional<Conjugator> pickRandomConjugator(List<Verb> verbs, List<Tense> tenses, VerbType verbType) {
    Optional<Verb> verb = pickRandomVerb(verbs, verbType);
    if (verb.isEmpty()) {
      return Optional.empty();
    }
    return verb.get().getRandomConjugator(tenses);
  }

  public static Adjective pickRandomAdjective(final List<Adjective> adjectives) {
    return adjectives.get(RANDOM.nextInt(adjectives.size()));
  }

  public static Noun pickRandomNoun(final List<Noun> nouns) {
    return nouns.get(RANDOM.nextInt(nouns.size()));
  }

  public static Optional<Noun> pickRandomNoun(final List<Noun> nouns, WordType wordType) {
    List<Noun> matchingNouns = nouns.stream().filter(o -> o.getWordType() == wordType).collect(Collectors.toList());
    if (matchingNouns.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingNouns.get(RANDOM.nextInt(matchingNouns.size())));
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
