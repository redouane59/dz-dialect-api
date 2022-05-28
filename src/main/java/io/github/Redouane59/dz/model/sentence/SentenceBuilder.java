package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.helper.GeneratorParameters;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.Adjective;
import io.github.Redouane59.dz.model.complement.Noun;
import io.github.Redouane59.dz.model.verb.RootTense;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.VerbType;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import io.github.Redouane59.dz.model.word.Sentence;
import io.github.Redouane59.dz.model.word.Sentence.SentenceContent;
import io.github.Redouane59.dz.model.word.Word;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SentenceBuilder {

  public static Random         RANDOM = new Random();
  private final SentenceSchema schema;
  List<WordTypeWordTuple> wordListFr;
  List<WordTypeWordTuple> wordListAr;
  private SentenceContent       sentenceContent;
  private Noun                  nounSubject;
  private Verb                  abstractVerb;
  private AbstractWord          abstractQuestion;
  private PossessiveWord        suffix;
  private GeneratorParameters   bodyArgs;
  private SentenceBuilderHelper helper;

  public SentenceBuilder(SentenceSchema sentenceSchema) {
    this.schema = sentenceSchema;
  }

  public Optional<Sentence> generate(GeneratorParameters bodyArgs) {
    this.bodyArgs   = bodyArgs;
    this.helper     = new SentenceBuilderHelper(bodyArgs, schema);
    sentenceContent = SentenceContent.builder().build();
    boolean resultOk = fillWordListFromSchema();
    if (!resultOk) {
      System.err.println("no sentence generated");
      return Optional.empty();
    }
    Sentence sentence = new Sentence();
    sentence.getTranslations().add(generateArTranslation(Lang.DZ));
    Translation frTranslation = generateFrTranslation();
    Translation dzTranslation = generateArTranslation(Lang.DZ);
    sentence.getTranslations().add(frTranslation);
    // word_propositions part
    sentenceContent.setRandomFrWords(helper.splitSentenceInWords(frTranslation.getValue()));
    sentenceContent.setRandomArWords(helper.splitSentenceInWords(dzTranslation.getValue()));
    // generating a second random sentence
    sentence.setContent(sentenceContent);
    addRandomWordPropositions(sentence);
    return Optional.of(sentence);
  }

  private void addRandomWordPropositions(Sentence sentence) {
    fillWordListFromSchema();
    sentence.getContent().getRandomFrWords().addAll(helper.splitSentenceInWords(generateFrTranslation().getValue()));
    sentence.getContent().getRandomArWords().addAll(helper.splitSentenceInWords(generateArTranslation(Lang.DZ).getValue()));
    Collections.shuffle(sentence.getContent().getRandomFrWords());
    Collections.shuffle(sentence.getContent().getRandomArWords());
  }

  private void resetAttributes() {
    abstractVerb     = null;
    nounSubject      = null;
    abstractQuestion = null;
    wordListFr       = new ArrayList<>();
    wordListAr       = new ArrayList<>();
    sentenceContent  = SentenceContent.builder().build();
    sentenceContent.setSentenceSchema(schema);
    if (schema.isPossibleNegation()) {
      if (bodyArgs.isPossibleNegation() && bodyArgs.isPossibleAffirmation()) {
        sentenceContent.setNegation(RANDOM.nextBoolean());
      } else if (bodyArgs.isPossibleNegation()) {
        sentenceContent.setNegation(true);
      }
    }
  }

  // @todo split FR & DZ
  private boolean fillWordListFromSchema() {
    PossessiveWord subject         = null;
    AbstractWord   abstractSubject = null;
    resetAttributes();
    for (int i = 0; i < schema.getFrSequence().size(); i++) {
      WordType wordType = schema.getFrSequence().get(i);
      switch (wordType) {
        case PRONOUN:
          AbstractWord abstractPronoun = helper.getRandomPronoun();
          PossessiveWord pronoun = abstractPronoun.getRandomConjugation();
          sentenceContent.setPronoun(pronoun);
          wordListFr.add(new WordTypeWordTuple(wordType, pronoun, i));
          wordListAr.add(new WordTypeWordTuple(wordType, pronoun, i));
          if (schema.getSubjectPosition() == i) {
            subject         = pronoun;
            abstractSubject = abstractPronoun;
          }
          break;
        case NOUN:
          Optional<Noun> abstractNoun = helper.getAbstractNoun(abstractVerb);
          if (abstractNoun.isEmpty()) {
            return false;
          }
          this.nounSubject = abstractNoun.get();
          PossessiveWord noun = new PossessiveWord(abstractNoun.get().getWordBySingular(true));
          Optional<GenderedWord> frArticle = helper.getArticle(noun, Lang.FR);
          Optional<GenderedWord> dzArticle = helper.getArticle(noun, Lang.DZ);
/*          if (article.isEmpty()) {
            return false;
          }*/
          if (abstractVerb != null && schema.getFrSequence().contains(WordType.PREPOSITION)) {
            if (abstractVerb.getVerbType() == VerbType.DEPLACEMENT) {
              wordListFr.add(new WordTypeWordTuple(WordType.PREPOSITION, abstractNoun.get().getDeplacementPreposition(), i));
              wordListAr.add(new WordTypeWordTuple(WordType.PREPOSITION, abstractNoun.get().getDeplacementPreposition(), i));
            } else if (abstractVerb.getVerbType() == VerbType.STATE) {
              wordListFr.add(new WordTypeWordTuple(WordType.PREPOSITION, abstractNoun.get().getStatePreposition(), i));
              wordListAr.add(new WordTypeWordTuple(WordType.PREPOSITION, abstractNoun.get().getStatePreposition(), i));
            }
          } // @todo dirty
          if (getFirstWordFromWordTypeFr(WordType.PREPOSITION, i) == null) {
            if (frArticle.isPresent()) {
              wordListFr.add(new WordTypeWordTuple(WordType.ARTICLE, frArticle.get(), i));
            }
            if (dzArticle.isPresent()) {
              wordListAr.add(new WordTypeWordTuple(WordType.ARTICLE, dzArticle.get(), i));
            }
          }

          sentenceContent.setAbstractNoun(nounSubject);
          wordListFr.add(new WordTypeWordTuple(wordType, noun, i));
          wordListAr.add(new WordTypeWordTuple(wordType, noun, i));
          if (schema.getSubjectPosition() == i) {
            subject         = noun;
            abstractSubject = nounSubject;
          }
          break;
        case VERB:
          Optional<Verb> abstractVerbOpt = helper.getAbstractVerb(abstractQuestion);
          if (abstractVerbOpt.isEmpty()) {
            return false;
          }
          abstractVerb = abstractVerbOpt.get();
          sentenceContent.setAbstractVerb(abstractVerb);
          Set<Tense> availableTenses = new HashSet<>();
          if (!schema.getTenses().isEmpty()) {
            if (schema.getTenses().contains(RootTense.IMPERATIVE)) {
              availableTenses.add(Tense.IMPERATIVE);
            } else {
              availableTenses = abstractVerb.getValues().stream().map(o -> (Conjugation) o).map(Conjugation::getTense)
                                            .filter(t -> bodyArgs.getTenses().contains(t.getRootTense()))
                                            .filter(t -> schema.getTenses().contains(t.getRootTense()))
                                            .collect(Collectors.toSet());
            }
          } else {
            availableTenses = abstractVerb.getValues().stream().map(o -> (Conjugation) o).map(Conjugation::getTense)
                                          .filter(t -> bodyArgs.getTenses().contains(t.getRootTense()))
                                          .filter(t -> t != Tense.IMPERATIVE)
                                          .collect(Collectors.toSet());
          }
          if (availableTenses.isEmpty()) {
            System.err.println("no tenses found");
            return false;
          }
          Tense tense = availableTenses.stream().skip(RANDOM.nextInt(availableTenses.size())).findFirst().get();
          sentenceContent.setTense(tense);
          if (tense == Tense.PAST || tense == Tense.PAST2) {
            sentenceContent.setNegation(false);
          }
          if (tense != Tense.IMPERATIVE) {
            wordListFr.add(new WordTypeWordTuple(wordType, helper.getVerbConjugation(abstractVerb, subject, tense, Lang.FR), i));
            wordListAr.add(new WordTypeWordTuple(wordType, helper.getVerbConjugation(abstractVerb, subject, tense, Lang.DZ), i));
          } else {
            PossessiveWord randomPronoun = AbstractWord.getRandomImperativePersonalPronoun();
            Optional<Conjugation> frConjugation = helper.getImperativeVerbConjugation(abstractVerb,
                                                                                      randomPronoun,
                                                                                      Lang.FR,
                                                                                      sentenceContent.isNegation());
            wordListFr.add(new WordTypeWordTuple(wordType, frConjugation.get(), i));
            Optional<Conjugation> arConjugation = helper.getImperativeVerbConjugation(abstractVerb,
                                                                                      randomPronoun,
                                                                                      Lang.DZ,
                                                                                      sentenceContent.isNegation());
            wordListAr.add(new WordTypeWordTuple(wordType, arConjugation.get(), i));
          }
          if (schema.getFrSequence().contains(WordType.SUFFIX)) {
            Optional<PossessiveWord> suffixOpt;
            if (sentenceContent.getTense() == Tense.IMPERATIVE) {
              suffixOpt = helper.getImperativeSuffix(abstractVerb.isObjectOnly());
            } else {
              suffixOpt = helper.getSuffix(subject.getPossession(), abstractVerb.isObjectOnly());
            }
            if (suffixOpt.isEmpty()) {
              return false;
            }
            suffix = suffixOpt.get();
            wordListFr.add(new WordTypeWordTuple(WordType.SUFFIX, suffix, i));
            wordListAr.add(new WordTypeWordTuple(WordType.SUFFIX, suffix, i));
          }
          break;
        case ADJECTIVE:
          Optional<Adjective> adjective = helper.getAbstractAdjective(abstractSubject, nounSubject);
          if (adjective.isEmpty()) {
            return false;
          }
          sentenceContent.setAbstractAdjective(adjective.get());
          wordListFr.add(new WordTypeWordTuple(wordType, helper.getAdjective(adjective.get(), subject, Lang.FR).get(), i));
          wordListAr.add(new WordTypeWordTuple(wordType, helper.getAdjective(adjective.get(), subject, Lang.DZ).get(), i));
          break;
        case ADVERB:
          AbstractWord adverb = helper.getAdverb();
          sentenceContent.setAbstractAdverb(adverb);
          wordListFr.add(new WordTypeWordTuple(wordType, (Word) adverb.getValues().get(0), i));
          wordListAr.add(new WordTypeWordTuple(wordType, (Word) adverb.getValues().get(0), i));
          break;
        case QUESTION:
          abstractQuestion = helper.getQuestion();
          sentenceContent.setAbstractQuestion(abstractQuestion);
          Word question = (Word) abstractQuestion.getValues().get(0);
          wordListFr.add(new WordTypeWordTuple(wordType, question, i));
          wordListAr.add(new WordTypeWordTuple(wordType, question, i));
      }
    }
    return true;
  }

  private Translation generateFrTranslation() {
    StringBuilder sentenceValue = new StringBuilder();
    for (int i = 0; i < schema.getFrSequence().size(); i++) {
      WordType wordType = schema.getFrSequence().get(i);
      if (wordType == WordType.SUFFIX && sentenceContent.getTense() == Tense.IMPERATIVE) { // add imperative condition
        sentenceValue.deleteCharAt(sentenceValue.length() - 1);
        sentenceValue.append("-");
      }
      if (wordType == WordType.VERB
          && sentenceContent.isNegation()
          && sentenceContent.getTense() != Tense.PAST
          && sentenceContent.getTense() != Tense.PAST2) {
        sentenceValue.append("ne "); // @todo use Traduction class and remove it
      }
      Word w = getFirstWordFromWordTypeFr(wordType, i);
      if (w != null) {
        sentenceValue.append(w.getTranslationValue(Lang.FR));
      }
      if (wordType == WordType.VERB
          && sentenceContent.isNegation()) { // @todo manage negative for past
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
    for (int i = 0; i < schema.getArSequence().size(); i++) {
      WordType wordType = schema.getArSequence().get(i);
      if (wordType == WordType.SUFFIX) {
        String suffixDzValue;
        sentenceValue.deleteCharAt(sentenceValue.length() - 1);
        sentenceValueAr.deleteCharAt(sentenceValueAr.length() - 1);
        if (abstractVerb.isDzOppositeComplement()) {
          suffixDzValue = AbstractWord.getOppositeSuffix(suffix).getTranslationValue(lang);
          sentenceValueAr.append(AbstractWord.getOppositeSuffix(suffix).getTranslationByLang(Lang.DZ).get().getArValue());
        } else {
          suffixDzValue = getFirstWordFromWordTypeFr(wordType, i).getTranslationValue(lang);
          sentenceValueAr.append(getFirstWordFromWordTypeFr(wordType, i).getTranslationByLang(lang).get().getArValue());
        }
        sentenceValue.append(suffixDzValue);
        // manage transformation here iou -> ih, ouou->ou, etc.
        for (Entry<String, String> m : DB.RULE_MAP.entrySet()) {
          sentenceValue = new StringBuilder(sentenceValue.toString().replace(m.getKey(), m.getValue()));
        }
      } else {
        if (sentenceContent.isNegation()) {
          if (wordType == WordType.VERB) {
            sentenceValue.append("ma ");
            sentenceValueAr.append("ما ");
          } else if (wordType == WordType.ADJECTIVE && sentenceContent.getAbstractAdjective() != null && sentenceContent.getAbstractAdjective()
                                                                                                                        .isDefinitive()) {
            sentenceValue.append("machi ");
            sentenceValueAr.append("ماشي ");
          }
        }
        Word   w       = getFirstWordFromWordTypeAr(wordType, i);
        String arValue = "";
        if (w != null) {
          sentenceValue.append(w.getTranslationValue(lang));
          arValue = w.getTranslationByLang(lang).get().getArValue();
        }
        sentenceValueAr.append(Objects.requireNonNullElse(arValue, " ٠٠٠ "));
        if (wordType == WordType.VERB && sentenceContent.isNegation()) {
          if (sentenceContent.getAbstractAdjective() == null || sentenceContent.getAbstractAdjective().isTemporal()) {
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

  // @todo dirty
  public Word getFirstWordFromWordTypeFr(WordType wordType, int position) {
    Optional<WordTypeWordTuple> opt = wordListFr.stream().filter(w -> w.getWordType() == wordType).findFirst();
    if (opt.isEmpty()) {
      return null;
    } else {
      return opt.get().getWord();
    }
  }

  public Word getFirstWordFromWordTypeAr(WordType wordType, int position) {
    Optional<WordTypeWordTuple> opt = wordListAr.stream().filter(w -> w.getWordType() == wordType).findFirst();
    if (opt.isEmpty()) {
      return null;
    } else {
      return opt.get().getWord();
    }
  }


  @AllArgsConstructor
  @Getter
  @Setter
  public static class WordTypeWordTuple {

    private WordType wordType;
    private Word     word;
    private int      position;
  }

}
