package io.github.Redouane59.dz.model.sentence;

import static io.github.Redouane59.dz.helper.Config.OBJECT_MAPPER;

import com.google.api.client.util.ArrayMap;
import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Articles.Article;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.question.Question;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.PersonalPronouns.PersonalPronoun;
import io.github.Redouane59.dz.model.verb.SuffixEnum;
import io.github.Redouane59.dz.model.verb.SuffixEnum.Suffix;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import io.github.Redouane59.dz.model.word.Sentence;
import io.github.Redouane59.dz.model.word.Sentence.SentenceContent;
import io.github.Redouane59.dz.model.word.Word;
import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class SentenceBuilder {

  public static Random RANDOM = new Random();
  Map<WordType, Word> wordMapFr;
  Map<WordType, Word> wordMapAr;
  private SentenceContent       sentenceContent;
  private SentenceSchema        schema;
  private Noun                  nounSubject;
  private Verb                  abstractVerb;
  private Question              question;
  private Suffix                suffix;
  private GeneratorParameters   bodyArgs;
  private SentenceBuilderHelper helper;

  @Deprecated
  public SentenceBuilder(String filePath) {
    try {
      schema = OBJECT_MAPPER.readValue(new File(filePath), SentenceSchema.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public SentenceBuilder(SentenceSchema sentenceSchema) {
    this.schema = sentenceSchema;
  }

  public Optional<Sentence> generate(GeneratorParameters bodyArgs) {
    this.bodyArgs   = bodyArgs;
    this.helper     = new SentenceBuilderHelper(bodyArgs, schema);
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

  private void resetAttributes() {
    abstractVerb = null;
    nounSubject  = null;
    question     = null;
    wordMapFr    = new ArrayMap<>();
    wordMapAr    = new ArrayMap<>();
  }

  private boolean fillWordMapsFromSchema() {
    PossessiveWord subject = null;
    resetAttributes();
    sentenceContent.setSentenceSchema(schema);
    if (schema.isPossibleNegation()) {
      if (bodyArgs.isPossibleNegation() && bodyArgs.isPossibleAffirmation()) {
        sentenceContent.setNegation(RANDOM.nextBoolean());
      } else if (bodyArgs.isPossibleNegation()) {
        sentenceContent.setNegation(true);
      }
    }
    for (int i = 0; i < schema.getFrSequence().size(); i++) {
      WordType wordType = schema.getFrSequence().get(i);
      switch (wordType) {
        case PRONOUN:
          PossessiveWord pronoun = helper.getPronoun();
          sentenceContent.setPronoun((PersonalPronoun) pronoun);
          wordMapFr.put(wordType, pronoun);
          wordMapAr.put(wordType, pronoun);
          if (schema.getSubjectPosition() == i) {
            subject = pronoun;
          }
          break;
        case NOUN:
          Optional<Noun> abstractNoun = helper.getAbstractNoun(abstractVerb);
          if (abstractNoun.isEmpty()) {
            return false;
          }
          this.nounSubject = abstractNoun.get();
          PossessiveWord noun = new PossessiveWord(abstractNoun.get().getWordBySingular(true));
          Optional<Article> article = helper.getArticle(noun, Lang.FR);
          if (article.isEmpty()) {
            return false;
          }
          if (abstractVerb != null && schema.getFrSequence().contains(WordType.PREPOSITION)) {
            if (abstractVerb.getVerbType() == VerbType.DEPLACEMENT) {
              wordMapFr.put(WordType.PREPOSITION, abstractNoun.get().getDeplacementPreposition());
              wordMapAr.put(WordType.PREPOSITION, abstractNoun.get().getDeplacementPreposition());
            } else if (abstractVerb.getVerbType() == VerbType.STATE) {
              wordMapFr.put(WordType.PREPOSITION, abstractNoun.get().getStatePreposition());
              wordMapAr.put(WordType.PREPOSITION, abstractNoun.get().getStatePreposition());
            }
          } // @todo dirty
          if (wordMapFr.get(WordType.PREPOSITION) == null) {
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
          Optional<Verb> abstractVerbOpt = helper.getAbstractVerb(question);
          if (abstractVerbOpt.isEmpty()) {
            return false;
          }
          abstractVerb = abstractVerbOpt.get();
          sentenceContent.setVerb(abstractVerb);
          Set<Tense> availableTenses = abstractVerb.getConjugators().stream().map(Conjugator::getTense)
                                                   .filter(t -> bodyArgs.getTenses().contains(t))
                                                   .filter(t -> t != Tense.IMPERATIVE)
                                                   .collect(Collectors.toSet());
          Tense tense = availableTenses.stream().skip(RANDOM.nextInt(availableTenses.size())).findFirst().get();
          sentenceContent.setTense(tense);
          wordMapFr.put(wordType, helper.getVerbConjugation(abstractVerb, subject, tense, Lang.FR));
          wordMapAr.put(wordType, helper.getVerbConjugation(abstractVerb, subject, tense, Lang.DZ));
          if (schema.getFrSequence().contains(WordType.SUFFIX)) {
            Optional<Suffix> suffixOpt = helper.getSuffix(subject, abstractVerb);
            if (suffixOpt.isEmpty()) {
              return false;
            }
            suffix = suffixOpt.get();
            wordMapFr.put(WordType.SUFFIX, suffix);
            wordMapAr.put(WordType.SUFFIX, suffix);
          }
          break;
        case ADJECTIVE:
          Optional<Adjective> adjective = helper.getAbstractAdjective(subject, nounSubject);
          if (adjective.isEmpty()) {
            return false;
          }
          sentenceContent.setAdjective(adjective.get());
          wordMapFr.put(wordType, helper.getAdjective(adjective.get(), subject, Lang.FR).get());
          wordMapAr.put(wordType, helper.getAdjective(adjective.get(), subject, Lang.DZ).get());
          break;
        case ADVERB:
          Adverb adverb = helper.getAdverb();
          sentenceContent.setAdverb(adverb);
          wordMapFr.put(wordType, adverb);
          wordMapAr.put(wordType, adverb);
          break;
        case QUESTION:
          question = helper.getQuestion();
          sentenceContent.setQuestion(question);
          wordMapFr.put(wordType, question.getWord());
          wordMapAr.put(wordType, question.getWord());
      }
    }
    return true;
  }

  private Translation generateFrTranslation() {
    StringBuilder sentenceValue = new StringBuilder();
    for (WordType wordType : schema.getFrSequence()) {
      if (wordType == WordType.VERB && sentenceContent.isNegation()) {
        sentenceValue.append("ne ");
      }
      sentenceValue.append(wordMapFr.get(wordType).getTranslationValue(Lang.FR));
      if (wordType == WordType.VERB && sentenceContent.isNegation()) {
        sentenceValue.append(" pas");
      }
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
        String suffixDzValue;
        sentenceValue.deleteCharAt(sentenceValue.length() - 1);
        sentenceValueAr.deleteCharAt(sentenceValueAr.length() - 1);
        if (abstractVerb.isDzOppositeComplement()) {
          suffixDzValue = SuffixEnum.getOppositeSuffix(suffix).getTranslationValue(lang);
          sentenceValueAr.append(SuffixEnum.getOppositeSuffix(suffix).getTranslationByLang(Lang.DZ).get().getArValue());
        } else {
          suffixDzValue = wordMapAr.get(wordType).getTranslationValue(lang);
          sentenceValueAr.append(wordMapAr.get(wordType).getTranslationByLang(lang).get().getArValue());
        }
        sentenceValue.append(suffixDzValue);
        // manage transformation here iou -> ih, ouou->ou, etc.
        for (Entry<String, String> m : SuffixEnum.RULE_MAP.entrySet()) {
          sentenceValue = new StringBuilder(sentenceValue.toString().replace(m.getKey(), m.getValue()));
        }
      } else {
        if (sentenceContent.isNegation()) {
          if (wordType == WordType.VERB) {
            sentenceValue.append("ma ");
            sentenceValueAr.append("ما ");
          } else if (wordType == WordType.ADJECTIVE && sentenceContent.getAdjective() != null && sentenceContent.getAdjective().isDefinitive()) {
            sentenceValue.append("machi ");
            sentenceValueAr.append("ماشي ");
          }
        }
        sentenceValue.append(wordMapAr.get(wordType).getTranslationValue(lang));
        String arValue = wordMapAr.get(wordType).getTranslationByLang(lang).get().getArValue();
        if (arValue != null) {
          sentenceValueAr.append(arValue);
        } else {
          sentenceValueAr.append(" ٠٠٠ ");
        }
        if (wordType == WordType.VERB && sentenceContent.isNegation()) {
          if (sentenceContent.getAdjective() == null || sentenceContent.getAdjective().isTemporal()) {
            sentenceValue.append("ch ");
            sentenceValueAr.append("ش");
          }
        }
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
      if (arabValue) {
        result += "!\u200F";
      } else {
        result += "!";
      }
    }
    return result;
  }

}
