package io.github.Redouane59.dz.model.sentence;

import static io.github.Redouane59.dz.model.sentence.SentenceBuilder.RANDOM;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.RootLang;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.Adjective;
import io.github.Redouane59.dz.model.complement.Noun;
import io.github.Redouane59.dz.model.complement.NounType;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SentenceBuilderHelper {

  GeneratorParameters bodyArgs;
  SentenceSchema      schema;

  public static PossessiveWord getRandomSuffix(final Possession other, boolean isDirect, boolean objectOnly) {
    Gender       randomGender     = Gender.getRandomGender();
    boolean      randomSingular   = RANDOM.nextBoolean();
    Possession   randomPossession = Possession.getRandomPosession(other, objectOnly);
    AbstractWord baseSuffix;
    if (isDirect) {
      baseSuffix = DB.DIRECT_SUFFIXES;
    } else {
      baseSuffix = DB.INDIRECT_SUFFIXES;
    }
    return baseSuffix.getValues().stream().map(o -> (PossessiveWord) o)
                     .filter(s -> s.isSingular() == randomSingular)
                     .filter(s -> s.getPossession() == randomPossession)
                     .filter(s -> s.getGender() == randomGender || s.getGender() == Gender.X || randomGender == Gender.X)
                     .findFirst().get();
  }

  public static PossessiveWord getRandomImperativeSuffix(boolean isDirect, boolean objectOnly) {
    Gender     randomGender   = Gender.getRandomGender();
    boolean    randomSingular = RANDOM.nextBoolean();
    Possession randomPossession;
    if (!objectOnly) {
      List<Possession> matchingPossessions = List.of(Possession.I, Possession.OTHER);
      randomPossession = matchingPossessions.get(RANDOM.nextInt(2));
    } else {
      randomPossession = Possession.OTHER;
    }

    AbstractWord baseSuffix;

    if (isDirect) {
      baseSuffix = DB.DIRECT_SUFFIXES;
    } else {
      baseSuffix = DB.INDIRECT_SUFFIXES;
    }

    return baseSuffix.getValues().stream().map(o -> (PossessiveWord) o)
                     .filter(s -> s.isSingular() == randomSingular)
                     .filter(s -> s.getPossession() == randomPossession)
                     .filter(s -> s.getGender() == randomGender || s.getGender() == Gender.X || randomGender == Gender.X)
                     .findFirst().get();
  }

  public Optional<PossessiveWord> getSuffix(Possession possession, boolean isObjectOnly) {
    boolean isDirect;
    isDirect = !schema.getFrSequence().contains(WordType.NOUN);
    return Optional.of(getRandomSuffix(possession, isDirect, isObjectOnly));
  }

  public Optional<PossessiveWord> getImperativeSuffix(boolean isObjectOnly) {
    boolean isDirect = !schema.getFrSequence().contains(WordType.NOUN);
    return Optional.of(getRandomImperativeSuffix(isDirect, isObjectOnly));
  }

  public AbstractWord getQuestion() {
    return DB.QUESTIONS.stream().skip(RANDOM.nextInt(DB.QUESTIONS.size())).findFirst().get();
  }

  public AbstractWord getAdverb() {
    return bodyArgs.getAdverbsFromIds().stream().skip(RANDOM.nextInt(bodyArgs.getAdverbsFromIds().size())).findFirst().get();
  }

  public AbstractWord getRandomPronoun() {
    return DB.PERSONAL_PRONOUNS.stream().skip(RANDOM.nextInt(DB.PERSONAL_PRONOUNS.size())).findFirst().get();
  }

  public Optional<GenderedWord> getArticle(PossessiveWord noun, Lang lang) {
    Optional<GenderedWord> article = AbstractWord.getDefinedArticleByCriterion(noun.getGender(lang), noun.isSingular());
    if (article.isEmpty()) {
      System.err.println("empty article");
      return Optional.empty();
    }
    return article;
  }

  public Optional<Verb> getAbstractVerb(AbstractWord question) {
    Set<Verb> verbs = bodyArgs.getVerbsFromIds();

    // arab translation
 /*   verbs = verbs.stream().filter(v -> v.getConjugators().stream()
                                        .anyMatch(c -> c.getConjugations().stream()
                                                        .anyMatch(x -> x.getDzTranslationAr() != null))).collect(Collectors.toSet());
*/
    if (schema.getTenses() != null) {
      verbs = verbs.stream()
                   .filter(v -> v.getValues().stream().map(o -> (Conjugation) o).anyMatch(c -> schema.getTenses().contains(c.getTense()))).collect(
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
      verbs = verbs.stream().filter(v -> v.getPossibleQuestionIds().contains(question.getId())).collect(Collectors.toSet());
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

  public Conjugation getVerbConjugation(Verb verb, PossessiveWord subject, Tense tense, Lang lang) {

    if (subject == null) {
      subject =
          getRandomPronoun().getConjugationByGenderSingularAndPossession(subject.getGender(), subject.isSingular(), Possession.OTHER, lang).get();
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

  public Optional<Conjugation> getImperativeVerbConjugation(Verb verb, PossessiveWord subject, Lang lang, boolean isNegative) {
    Tense tense;
    if (isNegative && lang.getRootLang() == RootLang.AR) {
      tense = Tense.PRESENT; // to manage exception in arabic
    } else {
      tense = Tense.IMPERATIVE;
    }
    return verb.getConjugationByGenderSingularPossessionAndTense(subject.getGender(lang),
                                                                 subject.isSingular(),
                                                                 subject.getPossession(),
                                                                 tense);
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

  public Optional<? extends GenderedWord> getAdjective(Adjective adjective, PossessiveWord subject, Lang lang) {
    if (subject == null) {
      System.err.println("null subject");
      return Optional.empty();
    }
    return adjective.getWordByGenderAndSingular(subject.getGender(lang), lang, subject.isSingular());
  }

  public Optional<Adjective> getAbstractAdjective(AbstractWord subject, Noun nounSubject) {
    Set<NounType> nounTypes = new HashSet<>();
    if (subject.getWordType() == WordType.PRONOUN) {
      nounTypes.add(NounType.PERSON);
    } else if (nounSubject != null) {
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
