package io.github.Redouane59.dz.model.generator;

import static io.github.Redouane59.dz.model.WordType.PLACE;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.sentence.Sentence;
import io.github.Redouane59.dz.model.sentence.Sentences;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SentenceGenerator {

  private final int      MAX_COUNT = 30;
  private       BodyArgs bodyArgs  = BodyArgs.builder().build();

  public Sentences generateRandomSentences() {
    Sentences      result       = new Sentences();
    Set<String>    errors       = new HashSet<>();
    List<Sentence> sentenceList = new ArrayList<>();
    if (bodyArgs.getCount() > MAX_COUNT) {
      System.err.println("max count limit reached with : " + bodyArgs.getCount());
      bodyArgs.setCount(MAX_COUNT);
    }
    for (int i = 0; i < bodyArgs.getCount(); i++) {
      Optional<Sentence> sentence = generateRandomSentence(bodyArgs);
      if (sentence.isPresent()) {
        sentenceList.add(sentence.get());
      } else {
        errors.add("No sentence generated because nothing was found matching input criterion");
      }
    }
    if (!errors.isEmpty()) {
      result.setErrors(errors);
    }
    result.setSentences(sentenceList);
    result.setCount(sentenceList.size());
    return result;
  }

  public Optional<Sentence> generateRandomSentence(BodyArgs bodyArgs) {

    // sentence with verb
    if (bodyArgs.getWordTypes().contains(WordType.VERB)) {
      Optional<VerbConjugation> verbConjugation = getRandomVerb(bodyArgs.getTenses());
      if (verbConjugation.isPresent()) {
        return generateRandomSentenceWithVerb(verbConjugation.get());
      }
    }
    return Optional.empty();

    // complement
    // @todo manage sentence without verb

  }

  public Optional<Sentence> generateRandomSentenceWithVerb(VerbConjugation verbConjugation) {
    Sentence sentence = new Sentence();

    sentence.setVerbIds(List.of(verbConjugation.getVerb().getId()));

    Optional<AbstractWord> complement = getRandomComplement(verbConjugation.getVerb());
    if (complement.isPresent()) {
      if (complement.get() instanceof Noun) {
        sentence.setNounIds(List.of(complement.get().getId()));
        sentence.addDzTranslation(getSubjectVerbNounTranslation(verbConjugation, complement.get(), Lang.DZ));
        sentence.addFrTranslation(getSubjectVerbNounTranslation(verbConjugation, complement.get(), Lang.FR));
      } else if (complement.get() instanceof Adjective) {
        sentence.setAdjectiveIds(List.of(complement.get().getId()));
        sentence.addDzTranslation(getSubjectVerbAdjectiveTranslation(verbConjugation.getConjugation(), complement.get(), Lang.DZ));
        sentence.addFrTranslation(getSubjectVerbAdjectiveTranslation(verbConjugation.getConjugation(), complement.get(), Lang.FR));
      }

    } else {
      sentence.addDzTranslation(verbConjugation.getConjugation().getDzTranslation());
      sentence.addFrTranslation(verbConjugation.getConjugation().getFrTranslation());
    }

    return Optional.of(sentence);
  }


  public String getSubjectVerbNounTranslation(VerbConjugation verbConjugation, AbstractWord complement, Lang lang) {
    String noun = complement.getTranslationBySingular(true, lang).getValue();

    String result = getProunounVerbString(verbConjugation.getConjugation(), lang);
    result += " ";
    result += verbConjugation.getVerb().getVerbType().getPlacePreposition(lang);
    result += " ";
    if (lang == Lang.FR) {
      result += complement.getWordBySingular(verbConjugation.getConjugation().isSingular(), Lang.FR).getGender().getFrArticle() + " ";
    }
    result += noun;

    return result;
  }

  public String getSubjectVerbAdjectiveTranslation(Conjugation conjugation, AbstractWord complement, Lang lang) {
    String subjectVerb = getProunounVerbString(conjugation, lang);
    String adjective   = complement.getTranslationByGender(conjugation.getGender(), conjugation.isSingular(), lang).getValue();
    return subjectVerb + " " + adjective;
  }

  public String getProunounVerbString(Conjugation conjugation, Lang lang) {
    String result = "";
    // adding pronoun is lang is on the config
    if (Config.DISPLAY_PROUNOUNS.contains(lang)) {
      result += conjugation.getPersonalPronoun(lang);
      result += " ";
    }
    // adding conjugation
    result += conjugation.getTranslationValue(lang);
    return result;
  }

  public String getNounVerbString(Conjugation conjugation, Lang lang) {
    String result     = "";
    Noun   randomNoun = DB.NOUNS.get(new Random().nextInt(DB.NOUNS.size()));
  }

  public Optional<VerbConjugation> getRandomVerb(List<Tense> tenses) {
    // get all the possible verbs
    List<Verb> matchingVerbs = DB.VERBS.stream()
                                       //  .filter(complement::contains)
                                       .filter(o -> o.getRandomConjugation(tenses).isPresent()).collect(Collectors.toList());
    // pick a random verb
    if (matchingVerbs.isEmpty()) {
      System.err.println("No verb found with matching given verbs & tenses");
      return Optional.empty();
    }
    Verb        randomVerb        = matchingVerbs.get(new Random().nextInt(matchingVerbs.size()));
    Conjugation randomConjugation = randomVerb.getRandomConjugation(tenses).get();
    return Optional.of(new VerbConjugation(randomVerb, randomConjugation));
  }


  public Optional<AbstractWord> getRandomComplement(Verb verb) {
    List<? extends AbstractWord> allComplements = new ArrayList<>();

    if (bodyArgs.getWordTypes().contains(PLACE)) {
      allComplements = Stream.concat(allComplements.stream(), DB.NOUNS.stream())
                             .collect(Collectors.toList());
    }
    if (bodyArgs.getWordTypes().contains(WordType.ADJECTIVE)) {
      allComplements = Stream.concat(allComplements.stream(), DB.ADJECTIVES.stream())
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

  public Optional<AbstractWord> getRandomComplement() {
    List<? extends AbstractWord> allComplements = Stream.concat(DB.NOUNS.stream(), DB.ADJECTIVES.stream())
                                                        .collect(Collectors.toList());
    return Optional.of(allComplements.get(new Random().nextInt(allComplements.size())));
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  public static class VerbConjugation {

    private Verb        verb;
    private Conjugation conjugation;
  }

}
