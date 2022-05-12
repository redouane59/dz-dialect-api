package io.github.Redouane59.dz.model.sentence;

import static io.github.Redouane59.dz.model.sentence.SentenceBuilder.RANDOM;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Articles;
import io.github.Redouane59.dz.model.Articles.Article;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.question.Question;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.PersonalPronouns;
import io.github.Redouane59.dz.model.verb.PersonalPronouns.PersonalPronoun;
import io.github.Redouane59.dz.model.verb.SuffixEnum;
import io.github.Redouane59.dz.model.verb.SuffixEnum.Suffix;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SentenceBuilderHelper {

  GeneratorParameters bodyArgs;
  SentenceSchema      schema;

  public Optional<Suffix> getSuffix(PossessiveWord copySuffix, Verb abstractVerb) {
    boolean isDirect;
    isDirect = !schema.getFrSequence().contains(WordType.NOUN);
    return Optional.of(SuffixEnum.getRandomSuffix(copySuffix.getPossession(), isDirect, abstractVerb.isObjectOnly()));
  }

  public Question getQuestion() {
    return Arrays.stream(Question.values()).skip(RANDOM.nextInt(Question.values().length)).findFirst().get();
  }

  public Adverb getAdverb() {
    return bodyArgs.getAdverbsFromIds().stream().skip(RANDOM.nextInt(bodyArgs.getAdverbsFromIds().size())).findFirst().get();
  }

  public PossessiveWord getPronoun() {
    DB.PERSONAL_PRONOUNS.hashCode();
    return PersonalPronouns.getRandomPersonalPronoun();
  }

  public Optional<Article> getArticle(PossessiveWord noun, Lang lang) {
    Optional<Article> article = Articles.getArticleByCriterion(noun.getGender(lang), noun.getPossession(), noun.isSingular(), true);
    if (article.isEmpty()) {
      System.err.println("empty article");
      return Optional.empty();
    }
    return article;
  }

  public Optional<Verb> getAbstractVerb(Question question) {
    Set<Verb> verbs = bodyArgs.getVerbsFromIds();

    // arab translation
 /*   verbs = verbs.stream().filter(v -> v.getConjugators().stream()
                                        .anyMatch(c -> c.getConjugations().stream()
                                                        .anyMatch(x -> x.getDzTranslationAr() != null))).collect(Collectors.toSet());
*/
    if (schema.getTenses() != null) {
      verbs = verbs.stream()
                   .filter(v -> v.getConjugators().stream().anyMatch(c -> schema.getTenses().contains(c.getTense()))).collect(
              Collectors.toSet());
      if (verbs.size() == 0) {
        System.out.println("no verb found based on tenses");
      }
    }
    if (schema.getVerbType() != null) {
      verbs = verbs.stream().filter(v -> v.getVerbType() == schema.getVerbType()).collect(Collectors.toSet());
      if (verbs.size() == 0) {
        System.out.println("no verb found based on type (" + schema.getVerbType() + " expected)");
      }
    }
    if (schema.getFrSequence().contains(WordType.QUESTION)) {
      verbs = verbs.stream().filter(v -> v.getPossibleQuestions().contains(question)).collect(Collectors.toSet());
      if (verbs.size() == 0) {
        System.out.println("no verb found based on question");
      }
    }
    if (schema.getFrSequence().contains(WordType.SUFFIX)) {
      if (schema.getFrSequence().contains(WordType.NOUN)) {
        verbs = verbs.stream().filter(Verb::isIndirectComplement)
                     .collect(Collectors.toSet());
      } else {
        verbs = verbs.stream().filter(Verb::isDirectComplement).collect(Collectors.toSet());
      }
      if (verbs.size() == 0) {
        System.out.println("no verb found based on suffix");
      }
    }
    if (schema.getFrSequence().contains(WordType.NOUN)) {
      verbs = verbs.stream().filter(v -> !v.getPossibleComplements().isEmpty())
                   .filter(v -> v.getPossibleComplements().size() > 1 || !v.getPossibleComplements().contains(NounType.ADVERB)) // @todo dirty
                   .collect(Collectors.toSet());
      if (verbs.size() == 0) {
        System.out.println("no verb found based on noun complements");
      }
    }
    if (verbs.size() == 0) {
      return Optional.empty();
    }
    return verbs.stream().skip(RANDOM.nextInt(verbs.size())).findFirst();
  }

  public PossessiveWord getVerbConjugation(Verb verb, PossessiveWord subject, Tense tense, Lang lang) {

    if (subject == null) {
      subject = PersonalPronouns.getRandomPersonalPronoun(true);
      tense   = Tense.IMPERATIVE;
    }
    Optional<Conjugation>
        conjugation =
        verb.getConjugationByGenderSingularPossessionAndTense(subject.getGender(lang),
                                                              subject.isSingular(),
                                                              subject.getPossession(),
                                                              tense);
    if (conjugation.isEmpty()) {
      System.err.println("no conjugation found for");
      return null;
    }
    return conjugation.get();
  }

  public Optional<Noun> getAbstractNoun(Verb abstractVerb) {
    Set<Noun> nouns = bodyArgs.getNounsFromIds();
    if (!schema.getNounTypes().isEmpty()) {
      nouns = nouns.stream().filter(n -> n.getNounTypes().stream()
                                          .anyMatch(n2 -> schema.getNounTypes().contains(n2))).collect(Collectors.toSet());
    }
    // case where the noun is the complement
    if (abstractVerb != null) {
      nouns = nouns.stream().filter(n -> n.getNounTypes().stream()
                                          .anyMatch(n2 -> abstractVerb.getPossibleComplements().contains(n2))).collect(Collectors.toSet());
    }
    if (nouns.isEmpty()) {
      System.err.println("nouns is empty");
      return Optional.empty();
    }
    return nouns.stream().skip(RANDOM.nextInt(nouns.size())).findFirst();
  }

  public Optional<GenderedWord> getAdjective(Adjective adjective, PossessiveWord subject, Lang lang) {
    if (subject == null) {
      System.err.println("null subject");
      return Optional.empty();
    }
    return adjective.getWordByGenderAndSingular(subject.getGender(lang), lang, subject.isSingular());
  }

  public Optional<Adjective> getAbstractAdjective(PossessiveWord subject, Noun nounSubject) {
    Set<NounType> nounTypes = new HashSet<>();
    if (subject instanceof PersonalPronoun) {
      nounTypes.add(NounType.PERSON);
    } else {
      nounTypes.addAll(nounSubject.getNounTypes());
    }
    Set<Adjective> adjectives = bodyArgs.getAdjectivesFromIds();
    if (!nounTypes.isEmpty()) {
      adjectives = adjectives.stream().filter(a -> a.getPossibleNouns().stream()
                                                    .anyMatch(nounTypes::contains)).collect(Collectors.toSet());
      if (adjectives.isEmpty()) {
        System.err.println("adjectives empty after noun types");
        return Optional.empty();
      }
    }

    if (schema.isDefinitiveAdjective()) {
      adjectives = adjectives.stream().filter(Adjective::isDefinitive).collect(Collectors.toSet());
    } else {
      adjectives = adjectives.stream().filter(Adjective::isTemporal).collect(Collectors.toSet());
    }
    if (adjectives.isEmpty()) {
      System.err.println("adjectives empty after is definitive");
      return Optional.empty();
    }

    Optional<Adjective> adjectiveOpt = adjectives.stream().skip(RANDOM.nextInt(adjectives.size())).findFirst();
    if (adjectiveOpt.isEmpty()) {
      System.err.println("adjective empty");
      return Optional.empty();
    }
    return adjectiveOpt;
  }

}
