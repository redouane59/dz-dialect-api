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
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.PersonalPronouns;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import io.github.Redouane59.dz.model.word.Sentence;
import io.github.Redouane59.dz.model.word.Word;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SentenceBuilder {

  Map<WordType, Word> frMap;
  Map<WordType, Word> arMap;
  // List<Word>          frWords = new ArrayList<>();
  // List<Word>          arWords = new ArrayList<>();
  private SentenceSchema schema;

  public SentenceBuilder(String filePath) {
    try {
      schema = OBJECT_MAPPER.readValue(new File(filePath), SentenceSchema.class);
      fillMapsAndWords();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Optional<Sentence> generate(GeneratorParameters bodyArgs) {
    return null;
  }

  public boolean isCompatible(GeneratorParameters bodyArgs) {
    return true;
  }

  public void fillMapsAndWords() {
    WordType subjectType = schema.getFrSequence().get(schema.getSubjectPosition());
  /*   subject = getWordFromWordType(subjectType);
   for (WordType wordType : schema.getFrSequence()) {
      PossessiveWord word = getWordFromWordType(wordType);
      frMap.put(wordType, word);
      arMap.put(wordType, word); // @todo use ar sequence
    } */
    fillWords();
  }

  public void fillWords() {
    PossessiveWord subject = null;
    frMap = new ArrayMap<>();
    arMap = new ArrayMap<>();
    for (int i = 0; i < schema.getFrSequence().size(); i++) {
      WordType wordType = schema.getFrSequence().get(i);
      switch (wordType) {
        case PRONOUN:
          PossessiveWord pronoun = getPronoun();
          frMap.put(wordType, pronoun);
          arMap.put(wordType, pronoun);
          if (schema.getSubjectPosition() == i) {
            subject = pronoun;
          }
          break;
        case NOUN:
          PossessiveWord noun = getNoun();
          Word article = getArticle(noun, Lang.FR);
          frMap.put(WordType.ARTICLE, article);
          arMap.put(WordType.ARTICLE, article);
          frMap.put(wordType, noun);
          arMap.put(wordType, noun);
          if (schema.getSubjectPosition() == i) {
            subject = noun;
          }
          break;
        case VERB:
          Verb verb = getAbstractVerb(subject);
          frMap.put(wordType, getVerbConjugation(verb, subject, Lang.FR));
          arMap.put(wordType, getVerbConjugation(verb, subject, Lang.DZ));
          break;
        case ADJECTIVE:
          Adjective adjective = getAbstractAdjective();
          frMap.put(wordType, getAdjective(adjective, subject, Lang.FR));
          arMap.put(wordType, getAdjective(adjective, subject, Lang.DZ));
          break;
      }
    }
  }


  public Translation generateFrTranslation() {
    fillWords(); // @todo bad
    StringBuilder sentenceValue = new StringBuilder();
    for (WordType wordType : schema.getFrSequence()) {
      sentenceValue.append(frMap.get(wordType).getTranslationValue(Lang.FR));
      sentenceValue.append(" ");
    }
    return new Translation(Lang.FR, sentenceValue.toString());
  }

  public Translation generateArTranslation() {
    StringBuilder sentenceValue = new StringBuilder();
    for (WordType wordType : schema.getArSequence()) {
      sentenceValue.append(arMap.get(wordType).getTranslationValue(Lang.DZ));
      sentenceValue.append(" ");
    }
    return new Translation(Lang.FR, sentenceValue.toString());
  }


  public PossessiveWord getPronoun() {
    DB.PERSONAL_PRONOUNS.hashCode();
    return PersonalPronouns.getRandomPersonalPronoun();
  }

  public GenderedWord getArticle(PossessiveWord noun, Lang lang) {
    Optional<Article> article = Articles.getArticleByCriterion(noun.getGender(lang), noun.getPossession(), noun.isSingular(), true);
    if (article.isEmpty()) {
      System.err.println("empty article");
    }
    return article.get();
  }

  public Verb getAbstractVerb(PossessiveWord subject) {
    Verb verb = DB.VERBS.stream().skip(RANDOM.nextInt(DB.VERBS.size())).findFirst().get();
    if (subject == null) {
      subject = PersonalPronouns.getRandomPersonalPronoun(true);
    }

    Set<Verb> verbsWithImperative = DB.VERBS.stream()
                                            .filter(v -> v.getConjugators().stream().anyMatch(c -> c.getTense() == Tense.IMPERATIVE)).collect(
            Collectors.toSet());
    return verbsWithImperative.stream().skip(RANDOM.nextInt(verbsWithImperative.size()))
                              .findFirst()
                              .get();
  }

  public PossessiveWord getVerbConjugation(Verb verb, PossessiveWord subject, Lang lang) {
    Tense tense = Tense.PRESENT;
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

  public PossessiveWord getNoun() {
    Noun noun = DB.NOUNS.stream().skip(RANDOM.nextInt(DB.NOUNS.size())).findFirst().get();
    return new PossessiveWord(noun.getWordBySingular(true));
  }

  public GenderedWord getAdjective(Adjective adjective, PossessiveWord subject, Lang lang) {
    return adjective.getWordByGenderAndSingular(subject.getGender(lang), lang, subject.isSingular());
  }

  public Adjective getAbstractAdjective() {
    return DB.ADJECTIVES.stream().skip(RANDOM.nextInt(DB.ADJECTIVES.size())).findFirst().get();
  }


}
