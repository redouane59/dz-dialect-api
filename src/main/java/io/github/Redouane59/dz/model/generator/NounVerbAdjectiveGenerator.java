package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.sentence.Sentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import io.github.Redouane59.dz.model.word.GenderedWord;
import java.util.List;
import java.util.Optional;

public class NounVerbAdjectiveGenerator extends AbstractSentenceGenerator {

  @Override
  public Optional<Sentence> generateSentence() {
    Sentence sentence = new Sentence();

    Optional<Verb> verb = this.getRandomInfinitiveVerb(VerbType.STATE);
    if (verb.isEmpty()) {
      return Optional.empty();
    }

    Noun         noun       = getRandomNoun(verb.get()).get();
    GenderedWord randomNoun = getRandomNounValue(noun).get();

    sentence.setVerbIds(List.of(verb.get().getId()));
    sentence.setNounIds(List.of(noun.getId()));

    Optional<Adjective> adjective = getRandomAdjective();
    if (adjective.isPresent()) {
      sentence.setAdjectiveIds(List.of(adjective.get().getId()));
      Tense randomTense = getRandomTense();
      sentence.addDzTranslation(getNounVerbAdjectiveTranslation(randomNoun, verb.get(), adjective.get(), Lang.DZ, randomTense));
      sentence.addFrTranslation(getNounVerbAdjectiveTranslation(randomNoun, verb.get(), adjective.get(), Lang.FR, randomTense));
    }
    return Optional.of(sentence);
  }

  private String getNounVerbAdjectiveTranslation(GenderedWord noun,
                                                 final Verb verb,
                                                 final Adjective adjective,
                                                 final Lang lang,
                                                 final Tense randomTense) {
    Optional<Conjugation> conjugation = verb.getConjugationByGenderSingularAndTense(noun.getGender(), noun.isSingular(), randomTense);
    if (conjugation.isEmpty()) {
      //  System.out.println("empty");
      conjugation = verb.getConjugationByGenderSingularAndTense(Gender.X, noun.isSingular(), randomTense);
    }
    String article;
    if (!noun.isSingular()) {
      article = Gender.X.getTranslationValue(lang, noun.getTranslationValue(lang));
    } else {
      article = noun.getGender().getTranslationValue(lang, noun.getTranslationValue(lang));
    }

    String conjugatedVerb = verb.getNounConjugation(noun.getGender(), noun.isSingular(), randomTense, lang);

    String accordedAdjectived = adjective.getTranslationByGender(noun.getGender(),
                                                                 conjugation.get().isSingular(),
                                                                 lang).getValue();

    String result = article;
    if (!article.isEmpty()) {
      result += " ";
    }
    result += noun.getTranslationValue(lang) + " " + conjugatedVerb + " " + accordedAdjectived;
    return result;
  }


}
