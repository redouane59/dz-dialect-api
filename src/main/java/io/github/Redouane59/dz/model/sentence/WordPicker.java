package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.question.Question;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class WordPicker {

  public static Random RANDOM = new Random();

  public static Optional<Verb> pickRandomVerb(Set<Verb> verbs) {
    if (verbs.isEmpty()) {
      System.err.println("adjectives list empty");
    }
    return verbs.stream().skip(RANDOM.nextInt(verbs.size())).findFirst();
  }

  public static Optional<Verb> pickRandomVerb(Set<Verb> verbs, Tense tense) {
    Set<Verb> matchingVerbs = verbs.stream().filter(o -> o.getConjugators().stream().anyMatch(a -> a.getTense() == tense))
                                   .collect(Collectors.toSet());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return matchingVerbs.stream().skip(RANDOM.nextInt(matchingVerbs.size())).findFirst();
  }

  public static Optional<Verb> pickRandomVerb(Set<Verb> verbs, Set<Tense> tenses, VerbType verbType) {
    Set<Verb> matchingVerbs = verbs.stream()
                                   .filter(o -> o.getVerbType() == verbType)
                                   .filter(o -> o.getConjugators().stream().anyMatch(a -> tenses.contains(a.getTense())))
                                   .collect(Collectors.toSet());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return matchingVerbs.stream().skip(RANDOM.nextInt(matchingVerbs.size())).findFirst();
  }

  public static Optional<Verb> pickRandomVerb(Set<Verb> verbs, Set<Tense> tenses) {
    Set<Verb> matchingVerbs = verbs.stream()
                                   .filter(o -> o.getConjugators().stream().anyMatch(a -> tenses.contains(a.getTense())))
                                   .collect(Collectors.toSet());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return matchingVerbs.stream().skip(RANDOM.nextInt(matchingVerbs.size())).findFirst();
  }

  public static Optional<Verb> pickRandomVerb(Set<Verb> verbs, VerbType verbType) {
    Set<Verb> matchingVerbs = verbs.stream().filter(o -> o.getVerbType() == verbType).collect(Collectors.toSet());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return matchingVerbs.stream().skip(RANDOM.nextInt(matchingVerbs.size())).findFirst();
  }

  public static Optional<Verb> pickRandomVerb(Set<Verb> verbs, boolean isReflexive) {
    Set<Verb> matchingVerbs = verbs.stream().filter(o -> (o.getReflexiveSuffixDz() != null) == isReflexive).collect(Collectors.toSet());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return matchingVerbs.stream().skip(RANDOM.nextInt(matchingVerbs.size())).findFirst();
  }

  public static Optional<Conjugator> pickRandomConjugator(Set<Verb> verbs, Set<Tense> tenses) {
    Optional<Verb> verb = pickRandomVerb(verbs);
    if (verb.isEmpty()) {
      return Optional.empty();
    }
    return verb.get().getRandomConjugator(tenses);
  }

  public static Optional<Conjugator> pickRandomConjugator(Set<Verb> verbs, Set<Tense> tenses, VerbType verbType) {
    Optional<Verb> verb = pickRandomVerb(verbs, verbType);
    if (verb.isEmpty()) {
      return Optional.empty();
    }
    return verb.get().getRandomConjugator(tenses);
  }

  public static Optional<Adjective> pickRandomAdjective(final Set<Adjective> adjectives, Set<NounType> compatibleNouns) {
    List<Adjective> matchingAdjectives = new ArrayList<>();
    if (compatibleNouns.isEmpty()) {
      System.out.println("No compatible noun found");
      return Optional.empty();
    }
    for (NounType nountype : compatibleNouns) {
      matchingAdjectives.addAll(adjectives.stream().filter(o -> o.getPossibleNouns().contains(nountype)).collect(Collectors.toSet()));
    }
    if (matchingAdjectives.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingAdjectives.get(RANDOM.nextInt(matchingAdjectives.size())));
  }

  public static Optional<Adjective> pickRandomAdjective(final Set<Adjective> adjectives) {
    Optional<Set<NounType>> nounTypes = getCompatibleNounsFromAdjectives(adjectives);
    if (nounTypes.isEmpty()) {
      return Optional.empty();
    }
    return pickRandomAdjective(adjectives, nounTypes.get());
  }

  public static Optional<Set<NounType>> getCompatibleNounsFromAdjectives(final Set<Adjective> adjectives) {
    return adjectives.stream().map(Adjective::getPossibleNouns).findAny();
  }

  public static Optional<Noun> pickRandomNoun(final Set<Noun> nouns, Set<NounType> nounTypes) {
    if (nouns.isEmpty() || nounTypes.isEmpty()) {
      System.err.println("nouns list empty");
      return Optional.empty();
    }
    List<Noun> matchingNouns = new ArrayList<>();
    for (NounType nountype : nounTypes) {
      matchingNouns.addAll(nouns.stream().filter(o -> o.getNounTypes().contains(nountype)).collect(Collectors.toSet()));
    }
    if (matchingNouns.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingNouns.get(RANDOM.nextInt(matchingNouns.size())));
  }

  public static Optional<Noun> pickRandomNoun(final Set<Noun> nouns, NounType wordType) {
    Set<Noun> matchingNouns = nouns.stream().filter(o -> o.getNounTypes().contains(wordType)).collect(Collectors.toSet());
    if (matchingNouns.isEmpty()) {
      return Optional.empty();
    }
    return matchingNouns.stream().skip(RANDOM.nextInt(matchingNouns.size())).findFirst();
  }

  public static Adverb pickRandomAdverb(Set<Adverb> adverbs) {
    return adverbs.stream().skip(RANDOM.nextInt(adverbs.size())).findFirst().orElse(null);
  }

  // @todo ponderation by tense
  public static Tense getRandomTense(Set<Tense> tenses) {
    return tenses.stream().skip(RANDOM.nextInt(tenses.size())).findFirst().orElse(null);
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

  /***
   * returns a tense from available tenses in a given verb and list of tenses
   */
  public static Tense getRandomTense(Verb verb, Set<Tense> tenses) {
    Set<Tense> matchingTenses = new HashSet<>();
    for (Tense tense : tenses) {
      if (verb.getConjugators().stream().anyMatch(o -> o.getTense() == tense)) {
        matchingTenses.add(tense);
      }
    }
    // return matchingTenses.get(RANDOM.nextInt(matchingTenses.size()));
    return matchingTenses.stream().skip(RANDOM.nextInt(matchingTenses.size())).findFirst().orElse(null);
  }


  public static Question pickRandomInterrogativePronoun(final Verb randomVerb) {
    return randomVerb.getPossibleQuestions().stream().skip(new Random().nextInt(randomVerb.getPossibleQuestions().size())).findFirst().orElse(null);
  }

  public static Set<Verb> getCompatibleVerbs(Set<Verb> verbs, Set<Noun> nouns) {
    Set<Verb> matchingVerbs = new HashSet<>();
    for (Noun noun : nouns) {
      for (NounType nounType : noun.getNounTypes()) {
        matchingVerbs.addAll(verbs.stream()
                                  .filter(v -> v.getPossibleComplements().contains(nounType)).collect(Collectors.toSet()));
      }
    }
    return matchingVerbs;
  }

}
