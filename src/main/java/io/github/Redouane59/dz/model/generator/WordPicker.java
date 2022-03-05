package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.model.Question;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.util.ArrayList;
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
    List<Verb> matchingVerbs = verbs.stream().filter(o -> o.getConjugators().stream().anyMatch(a -> a.getTense() == tense))
                                    .collect(Collectors.toList());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingVerbs.get(RANDOM.nextInt(matchingVerbs.size())));
  }

  public static Optional<Verb> pickRandomVerb(List<Verb> verbs, List<Tense> tenses, VerbType verbType) {
    List<Verb> matchingVerbs = verbs.stream()
                                    .filter(o -> o.getVerbType() == verbType)
                                    .filter(o -> o.getConjugators().stream().anyMatch(a -> tenses.contains(a.getTense())))
                                    .collect(Collectors.toList());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingVerbs.get(RANDOM.nextInt(matchingVerbs.size())));
  }

  public static Optional<Verb> pickRandomVerb(List<Verb> verbs, List<Tense> tenses) {
    List<Verb> matchingVerbs = verbs.stream()
                                    .filter(o -> o.getConjugators().stream().anyMatch(a -> tenses.contains(a.getTense())))
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

  public static Optional<Adjective> pickRandomAdjective(final List<Adjective> adjectives, List<NounType> compatibleNouns) {
    List<Adjective> matchingAdjectives = new ArrayList<>();
    if (compatibleNouns.isEmpty()) {
      System.out.println("No compatible noun found");
      return Optional.empty();
    }
    for (NounType nountype : compatibleNouns) {
      matchingAdjectives.addAll(adjectives.stream().filter(o -> o.getPossibleNouns().contains(nountype)).collect(Collectors.toList()));
    }
    if (matchingAdjectives.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingAdjectives.get(RANDOM.nextInt(matchingAdjectives.size())));
  }

  public static Optional<Adjective> pickRandomAdjective(final List<Adjective> adjectives) {
    Optional<List<NounType>> nounTypes = getCompatibleNounsFromAdjectives(adjectives);
    if (nounTypes.isEmpty()) {
      return Optional.empty();
    }
    return pickRandomAdjective(adjectives, nounTypes.get());
  }

  public static Optional<List<NounType>> getCompatibleNounsFromAdjectives(final List<Adjective> adjectives) {
    return adjectives.stream().map(Adjective::getPossibleNouns).findAny();
  }

  public static Optional<Noun> pickRandomNoun(final List<Noun> nouns, List<NounType> nounTypes) {
    if (nouns.isEmpty() || nounTypes.isEmpty()) {
      System.err.println("nouns list empty");
      return Optional.empty();
    }
    List<Noun> matchingNouns = new ArrayList<>();
    for (NounType nountype : nounTypes) {
      matchingNouns.addAll(nouns.stream().filter(o -> o.getNounTypes().contains(nountype)).collect(Collectors.toList()));
    }
    if (matchingNouns.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingNouns.get(RANDOM.nextInt(matchingNouns.size())));
  }

  public static Optional<Noun> pickRandomNoun(final List<Noun> nouns, NounType wordType) {
    List<Noun> matchingNouns = nouns.stream().filter(o -> o.getNounTypes().contains(wordType)).collect(Collectors.toList());
    if (matchingNouns.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingNouns.get(RANDOM.nextInt(matchingNouns.size())));
  }

  public static Adverb pickRandomAdverb(List<Adverb> adverbs) {
    return adverbs.get(RANDOM.nextInt(adverbs.size()));
  }

  // @todo ponderation by tense
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


  public static Question pickRandomInterrogativePronoun(final Verb randomVerb) {
    return randomVerb.getPossibleQuestions().get(RANDOM.nextInt(randomVerb.getPossibleQuestions().size()));
  }

  public static List<Verb> getCompatibleVerbs(List<Verb> verbs, List<Noun> nouns) {
    List<Verb> matchingVerbs = new ArrayList<>();
    for (Noun noun : nouns) {
      for (NounType nounType : noun.getNounTypes()) {
        matchingVerbs.addAll(verbs.stream()
                                  .filter(v -> v.getPossibleComplements().contains(nounType)).collect(Collectors.toList()));
      }
    }
    return matchingVerbs;
  }

}
