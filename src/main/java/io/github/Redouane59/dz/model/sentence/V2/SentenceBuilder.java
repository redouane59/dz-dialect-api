package io.github.Redouane59.dz.model.sentence.V2;

import static io.github.Redouane59.dz.helper.Config.OBJECT_MAPPER;
import static io.github.Redouane59.dz.model.sentence.WordPicker.RANDOM;

import com.google.api.client.util.ArrayMap;
import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Articles;
import io.github.Redouane59.dz.model.Articles.Article;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.question.Question;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.PersonalPronouns;
import io.github.Redouane59.dz.model.verb.PersonalPronouns.PersonalPronoun;
import io.github.Redouane59.dz.model.verb.SuffixEnum;
import io.github.Redouane59.dz.model.verb.SuffixEnum.Suffix;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import io.github.Redouane59.dz.model.word.Sentence;
import io.github.Redouane59.dz.model.word.Sentence.SentenceContent;
import io.github.Redouane59.dz.model.word.Word;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SentenceBuilder {

  Map<WordType, Word> wordMapFr;
  Map<WordType, Word> wordMapAr;
  private SentenceContent     sentenceContent;
  private SentenceSchema      schema;
  private Noun                nounSubject;
  private Verb                abstractVerb;
  private Question            question;
  private Suffix              suffix;
  private GeneratorParameters bodyArgs;

  public SentenceBuilder(String filePath) {
    try {
      schema = OBJECT_MAPPER.readValue(new File(filePath), SentenceSchema.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Optional<Sentence> generate(GeneratorParameters bodyArgs) {
    this.bodyArgs   = bodyArgs;
    sentenceContent = SentenceContent.builder().build();
    boolean resultOk = fillWordMapsFromSchema();
    if (!resultOk) {
      System.err.println("no sentence generated");
      return Optional.empty();
    }
    Sentence sentence = new Sentence();
    sentence.getTranslations().add(generateArTranslation(Lang.DZ));
    sentence.getTranslations().add(generateFrTranslation());
    sentence.setContent(sentenceContent);
    return Optional.of(sentence);
  }

  public boolean isCompatible(GeneratorParameters bodyArgs) {
    return true;
  }

  public boolean fillWordMapsFromSchema() {
    PossessiveWord subject = null;
    wordMapFr = new ArrayMap<>();
    wordMapAr = new ArrayMap<>();
    for (int i = 0; i < schema.getFrSequence().size(); i++) {
      WordType wordType = schema.getFrSequence().get(i);
      switch (wordType) {
        case PRONOUN:
          PossessiveWord pronoun = getPronoun();
          sentenceContent.setPronoun((PersonalPronoun) pronoun);
          wordMapFr.put(wordType, pronoun);
          wordMapAr.put(wordType, pronoun);
          if (schema.getSubjectPosition() == i) {
            subject = pronoun;
          }
          break;
        case NOUN:
          Optional<Noun> abstractNoun = getAbstractNoun();
          if (abstractNoun.isEmpty()) {
            return false;
          }
          this.nounSubject = abstractNoun.get();
          PossessiveWord noun = new PossessiveWord(abstractNoun.get().getWordBySingular(true));
          Optional<Article> article = getArticle(noun, Lang.FR);
          if (article.isEmpty()) {
            return false;
          }
          if (abstractVerb.getVerbType() == VerbType.DEPLACEMENT) {
            wordMapFr.put(WordType.PREPOSITION, abstractNoun.get().getDeplacementPreposition());
            wordMapAr.put(WordType.PREPOSITION, abstractNoun.get().getDeplacementPreposition());
          } else if (abstractVerb.getVerbType() == VerbType.STATE) {
            wordMapFr.put(WordType.PREPOSITION, abstractNoun.get().getStatePreposition());
            wordMapAr.put(WordType.PREPOSITION, abstractNoun.get().getStatePreposition());
          } else { // @todo dirty
            wordMapFr.put(WordType.ARTICLE, article.get());
            wordMapAr.put(WordType.ARTICLE, article.get());
          }
          sentenceContent.setNoun(nounSubject);
          wordMapFr.put(wordType, noun);
          wordMapAr.put(wordType, noun);
          if (schema.getSubjectPosition() == i) {
            subject = noun;
          }
          break;
        case VERB:
          Optional<Verb> abstractVerbOpt = getAbstractVerb();
          if (abstractVerbOpt.isEmpty()) {
            return false;
          }
          abstractVerb = getAbstractVerb().get();
          sentenceContent.setVerb(abstractVerb);
          wordMapFr.put(wordType, getVerbConjugation(abstractVerb, subject, Lang.FR));
          wordMapAr.put(wordType, getVerbConjugation(abstractVerb, subject, Lang.DZ));
          break;
        case ADJECTIVE:
          Optional<Adjective> adjective = getAbstractAdjective(subject);
          if (adjective.isEmpty()) {
            return false;
          }
          wordMapFr.put(wordType, getAdjective(adjective.get(), subject, Lang.FR).get());
          wordMapAr.put(wordType, getAdjective(adjective.get(), subject, Lang.DZ).get());
          break;
        case ADVERB:
          Adverb adverb = getAdverb();
          sentenceContent.setAdverb(adverb);
          wordMapFr.put(wordType, adverb);
          wordMapAr.put(wordType, adverb);
          break;
        case QUESTION:
          question = getQuestion();
          sentenceContent.setQuestion(question);
          wordMapFr.put(wordType, question.getWord());
          wordMapAr.put(wordType, question.getWord());
        case SUFFIX:
          suffix = getSuffix(subject);
          wordMapFr.put(wordType, suffix);
          wordMapAr.put(wordType, suffix);
      }
    }
    return true;
  }

  private Suffix getSuffix(PossessiveWord s) {
    return SuffixEnum.getRandomSuffix(s.getPossession());
  }

  private Question getQuestion() {
    return Arrays.stream(Question.values()).skip(RANDOM.nextInt(Question.values().length)).findFirst().get();
  }

  private Adverb getAdverb() {
    return bodyArgs.getAdverbsFromIds().stream().skip(RANDOM.nextInt(bodyArgs.getAdverbsFromIds().size())).findFirst().get();
  }

  private Translation generateFrTranslation() {
    StringBuilder sentenceValue = new StringBuilder();
    for (WordType wordType : schema.getFrSequence()) {
      sentenceValue.append(wordMapFr.get(wordType).getTranslationValue(Lang.FR));
      sentenceValue.append(" ");
    }
    sentenceValue.append(completeSentence(false));
    return new Translation(Lang.FR, sentenceValue.toString());
  }

  private Translation generateArTranslation(Lang lang) {
    StringBuilder sentenceValue   = new StringBuilder();
    StringBuilder sentenceValueAr = new StringBuilder();
    for (WordType wordType : schema.getArSequence()) {
      if (wordType == WordType.SUFFIX) {
        sentenceValue.deleteCharAt(sentenceValue.length() - 1);
        sentenceValueAr.deleteCharAt(sentenceValueAr.length() - 1);
        if (abstractVerb.isDzOppositeComplement()) {
          sentenceValue.append(SuffixEnum.getOppositeSuffix(suffix).getTranslationValue(lang));
          sentenceValueAr.append(SuffixEnum.getOppositeSuffix(suffix).getTranslationByLang(Lang.DZ).get().getArValue());

        } else {
          sentenceValue.append(wordMapAr.get(wordType).getTranslationValue(lang));
          sentenceValueAr.append(wordMapAr.get(wordType).getTranslationByLang(lang).get().getArValue());
        }
      } else {
        sentenceValue.append(wordMapAr.get(wordType).getTranslationValue(lang));
        sentenceValueAr.append(wordMapAr.get(wordType).getTranslationByLang(lang).get().getArValue());
      }
      sentenceValue.append(" ");
      sentenceValueAr.append(" ");
    }
    sentenceValue.append(completeSentence(false));
    sentenceValueAr.append(completeSentence(true));
    return new Translation(lang, sentenceValue.toString(), sentenceValueAr.toString());
  }

  private String completeSentence(boolean arabValue) {
    String result = "";
    if (schema.getFrSequence().contains(WordType.QUESTION)) {
      if (arabValue) {
        result += "؟";
      } else {
        result += "?";
      }
    }
    if (schema.getFrSequence().size() == 1 && schema.getFrSequence().get(0) == WordType.VERB) {
      result += "!";
    }
    return result;
  }

  private PossessiveWord getPronoun() {
    DB.PERSONAL_PRONOUNS.hashCode();
    return PersonalPronouns.getRandomPersonalPronoun();
  }

  private Optional<Article> getArticle(PossessiveWord noun, Lang lang) {
    Optional<Article> article = Articles.getArticleByCriterion(noun.getGender(lang), noun.getPossession(), noun.isSingular(), true);
    if (article.isEmpty()) {
      System.err.println("empty article");
      return Optional.empty();
    }
    return article;
  }

  private Optional<Verb> getAbstractVerb() {
    Set<Verb> verbs = bodyArgs.getVerbsFromIds();

    verbs = verbs.stream().filter(v -> v.getConjugators().stream()
                                        .anyMatch(c -> c.getConjugations().stream()
                                                        .anyMatch(x -> x.getDzTranslationAr() != null))).collect(Collectors.toSet());

    if (schema.getTenses() != null) {
      verbs = verbs.stream()
                   .filter(v -> v.getConjugators().stream().anyMatch(c -> schema.getTenses().contains(c.getTense()))).collect(
              Collectors.toSet());
    }
    if (schema.getVerbType() != null) {
      verbs = verbs.stream().filter(v -> v.getVerbType() == schema.getVerbType()).collect(Collectors.toSet());
    }
    if (schema.getFrSequence().contains(WordType.QUESTION)) {
      verbs = verbs.stream().filter(v -> v.getPossibleQuestions().contains(question)).collect(Collectors.toSet());
    }
    if (suffix != null) {
      verbs = verbs.stream().filter(v -> (v.isDirectComplement() && suffix.isDirect())
                                         || (v.isIndirectComplement() && !suffix.isDirect())).collect(Collectors.toSet());
    }
    if (verbs.size() == 0) {
      System.err.println("no verbs matching criterion");
    }

    return verbs.stream().skip(RANDOM.nextInt(verbs.size())).findFirst();
  }

  private PossessiveWord getVerbConjugation(Verb verb, PossessiveWord subject, Lang lang) {
    Tense tense = Tense.PRESENT; // @todo update
    if (subject == null) {
      subject = PersonalPronouns.getRandomPersonalPronoun(true);
      tense   = Tense.IMPERATIVE;
    }
    sentenceContent.setTense(tense);
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

  private Optional<Noun> getAbstractNoun() {
    Set<Noun> nouns = bodyArgs.getNounsFromIds();
    if (!schema.getNounTypes().isEmpty()) {
      nouns = nouns.stream().filter(n -> n.getNounTypes().stream()
                                          .anyMatch(n2 -> schema.getNounTypes().contains(n2))).collect(Collectors.toSet());
    }
    if (nouns.isEmpty()) {
      System.err.println("nouns is empty");
      return Optional.empty();
    }
    return nouns.stream().skip(RANDOM.nextInt(nouns.size())).findFirst();
  }

  private Optional<GenderedWord> getAdjective(Adjective adjective, PossessiveWord subject, Lang lang) {
    if (subject == null) {
      System.err.println("null subject");
      return Optional.empty();
    }
    return adjective.getWordByGenderAndSingular(subject.getGender(lang), lang, subject.isSingular());
  }

  private Optional<Adjective> getAbstractAdjective(PossessiveWord subject) {
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
    }
    if (adjectives.isEmpty()) {
      System.err.println("adjectives empty");
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
