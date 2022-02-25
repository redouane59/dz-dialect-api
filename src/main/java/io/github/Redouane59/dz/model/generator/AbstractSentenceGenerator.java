package io.github.Redouane59.dz.model.generator;

import static io.github.Redouane59.dz.model.WordType.PLACE;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.sentence.Sentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.GenderedWord;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public abstract class AbstractSentenceGenerator {

  private final BodyArgs bodyArgs = BodyArgs.builder().build();

  public abstract Optional<Sentence> generateSentence();

  public Optional<VerbConjugation> getRandomVerb() {
    // get all the possible verbs
    List<Verb> matchingVerbs = bodyArgs.getVerbsFromIds().stream()
                                       //  .filter(complement::contains)
                                       .filter(o -> o.getRandomConjugationByTenses(bodyArgs.getTenses()).isPresent()).collect(Collectors.toList());
    // pick a random verb
    if (matchingVerbs.isEmpty()) {
      System.err.println("No verb found with matching given verbs & tenses");
      return Optional.empty();
    }
    Verb        randomVerb        = matchingVerbs.get(new Random().nextInt(matchingVerbs.size()));
    Conjugation randomConjugation = randomVerb.getRandomConjugation(randomVerb.getRandomConjugator(bodyArgs.getTenses()).get()).get();
    return Optional.of(new VerbConjugation(randomVerb, randomConjugation));
  }

  public Optional<Verb> getRandomInfinitiveVerb(VerbType verbType) {
    List<Verb> matchingVerbs = bodyArgs.getVerbsFromIds().stream().filter(o -> o.getVerbType() == verbType).collect(Collectors.toList());
    if (matchingVerbs.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingVerbs.get(new Random().nextInt(matchingVerbs.size())));
  }

  public Optional<Noun> getRandomNoun(Verb verb) {
    // get all the possible complements
    List<Noun> matchingComplements = bodyArgs.getNounsFromIds().stream()
                                             .filter(o -> verb.getPossibleComplements().contains(o.getWordType()))
                                             .filter(o -> bodyArgs.getWordTypes().contains(o.getWordType()))
                                             .collect(Collectors.toList());
    if (matchingComplements.isEmpty()) {
      System.err.println("no complement found");
      return Optional.empty();
    }
    // pick a random root
    return Optional.of(matchingComplements.get(new Random().nextInt(matchingComplements.size())));
  }

  public Optional<Noun> getRandomNoun(Verb verb, WordType wordType) {
    // get all the possible complements
    List<Noun> matchingComplements = bodyArgs.getNounsFromIds().stream()
                                             .filter(o -> verb.getPossibleComplements().contains(wordType))
                                             .filter(o -> bodyArgs.getWordTypes().contains(o.getWordType()))
                                             .collect(Collectors.toList());
    if (matchingComplements.isEmpty()) {
      System.err.println("no complement found");
      return Optional.empty();
    }
    // pick a random root
    return Optional.of(matchingComplements.get(new Random().nextInt(matchingComplements.size())));
  }

  public Optional<GenderedWord> getRandomNounValue(Noun noun) {
    // pick a random root
    return Optional.of(noun.getValues().get(new Random().nextInt(noun.getValues().size())));
  }

  public Optional<AbstractWord> getRandomComplement(Verb verb) {
    List<? extends AbstractWord> allComplements = new ArrayList<>();

    if (bodyArgs.getWordTypes().contains(PLACE)) {
      allComplements = Stream.concat(allComplements.stream(), bodyArgs.getNounsFromIds().stream())
                             .collect(Collectors.toList());
    }
    if (bodyArgs.getWordTypes().contains(WordType.ADJECTIVE)) {
      allComplements = Stream.concat(allComplements.stream(), bodyArgs.getAdjectivesFromIds().stream())
                             .collect(Collectors.toList());
    }

    // get all the possible complements
    List<? extends AbstractWord> matchingComplements = allComplements.stream()
                                                                     .filter(o -> verb.getPossibleComplements().contains(o.getWordType()))
                                                                     .filter(o -> bodyArgs.getWordTypes().contains(o.getWordType()))
                                                                     .collect(Collectors.toList());
    if (matchingComplements.isEmpty()) {
      System.err.println("no complement found");
      return Optional.empty();
    }

    // pick a random root
    return Optional.of(matchingComplements.get(new Random().nextInt(matchingComplements.size())));
  }

  public String getProunounVerbString(Conjugation conjugation, Lang lang) {
    String result = "";
    // adding pronoun is lang is on the config
    if (Config.DISPLAY_PROUNOUNS.contains(lang)) {
      result += conjugation.getPersonalPronoun(lang, false);
      result += " ";
    }
    // adding conjugation
    result += conjugation.getTranslationValue(lang);
    return result;
  }


  public Optional<AbstractWord> getRandomComplement() {
    List<? extends AbstractWord> allComplements = Stream.concat(bodyArgs.getNounsFromIds().stream(), bodyArgs.getAdjectivesFromIds().stream())
                                                        .collect(Collectors.toList());
    return Optional.of(allComplements.get(new Random().nextInt(allComplements.size())));
  }


  public String getSubjectVerbAdjectiveTranslation(Conjugation conjugation, AbstractWord complement, Lang lang) {
    String subjectVerb = getProunounVerbString(conjugation, lang);
    String adjective   = complement.getTranslationByGender(conjugation.getGender(), conjugation.isSingular(), lang).getValue();
    return subjectVerb + " " + adjective;
  }

  public Optional<Adjective> getRandomAdjective() {
    return Optional.of(bodyArgs.getAdjectivesFromIds().get(new Random().nextInt(bodyArgs.getAdjectivesFromIds().size())));
  }

  public Tense getRandomTense() {
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

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  public static class VerbConjugation {

    private Verb        verb;
    private Conjugation conjugation;
  }

}
