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
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.PersonalPronouns;
import io.github.Redouane59.dz.model.verb.PersonalPronouns.PersonalPronoun;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import io.github.Redouane59.dz.model.word.Sentence;
import io.github.Redouane59.dz.model.word.Word;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SentenceBuilder {

  Map<WordType, Word> wordMapFr;
  Map<WordType, Word> wordMapAr;
  private SentenceSchema schema;
  private Noun           nounSubject;

  public SentenceBuilder(String filePath) {
    try {
      schema = OBJECT_MAPPER.readValue(new File(filePath), SentenceSchema.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Optional<Sentence> generate(GeneratorParameters bodyArgs) {
    fillWordMapsFromSchema();
    Sentence sentence = new Sentence();
    sentence.getTranslations().add(generateArTranslation(Lang.DZ));
    sentence.getTranslations().add(generateFrTranslation());
    return Optional.of(sentence);
  }

  public boolean isCompatible(GeneratorParameters bodyArgs) {
    return true;
  }

  public void fillWordMapsFromSchema() {
    PossessiveWord subject = null;
    wordMapFr = new ArrayMap<>();
    wordMapAr = new ArrayMap<>();
    for (int i = 0; i < schema.getFrSequence().size(); i++) {
      WordType wordType = schema.getFrSequence().get(i);
      switch (wordType) {
        case PRONOUN:
          PossessiveWord pronoun = getPronoun();
          wordMapFr.put(wordType, pronoun);
          wordMapAr.put(wordType, pronoun);
          if (schema.getSubjectPosition() == i) {
            subject = pronoun;
          }
          break;
        case NOUN:
          PossessiveWord noun = getNoun();
          Word article = getArticle(noun, Lang.FR);
          wordMapFr.put(WordType.ARTICLE, article);
          wordMapAr.put(WordType.ARTICLE, article);
          wordMapFr.put(wordType, noun);
          wordMapAr.put(wordType, noun);
          if (schema.getSubjectPosition() == i) {
            subject = noun;
          }
          break;
        case VERB:
          Verb verb = getAbstractVerb();
          wordMapFr.put(wordType, getVerbConjugation(verb, subject, Lang.FR));
          wordMapAr.put(wordType, getVerbConjugation(verb, subject, Lang.DZ));
          break;
        case ADJECTIVE:
          Adjective adjective = getAbstractAdjective(subject);
          wordMapFr.put(wordType, getAdjective(adjective, subject, Lang.FR));
          wordMapAr.put(wordType, getAdjective(adjective, subject, Lang.DZ));
          break;
      }
    }
  }

  private Translation generateFrTranslation() {
    StringBuilder sentenceValue = new StringBuilder();
    for (WordType wordType : schema.getFrSequence()) {
      sentenceValue.append(wordMapFr.get(wordType).getTranslationValue(Lang.FR));
      sentenceValue.append(" ");
    }
    return new Translation(Lang.FR, sentenceValue.toString());
  }

  private Translation generateArTranslation(Lang lang) {
    StringBuilder sentenceValue   = new StringBuilder();
    StringBuilder sentenceValueAr = new StringBuilder();
    for (WordType wordType : schema.getArSequence()) {
      sentenceValue.append(wordMapAr.get(wordType).getTranslationValue(lang));
      sentenceValue.append(" ");
      sentenceValueAr.append(wordMapAr.get(wordType).getTranslationByLang(lang).get().getArValue());
      sentenceValueAr.append(" ");
    }
    return new Translation(lang, sentenceValue.toString(), sentenceValueAr.toString());
  }

  private PossessiveWord getPronoun() {
    DB.PERSONAL_PRONOUNS.hashCode();
    return PersonalPronouns.getRandomPersonalPronoun();
  }

  private GenderedWord getArticle(PossessiveWord noun, Lang lang) {
    Optional<Article> article = Articles.getArticleByCriterion(noun.getGender(lang), noun.getPossession(), noun.isSingular(), true);
    if (article.isEmpty()) {
      System.err.println("empty article");
    }
    return article.get();
  }

  private Verb getAbstractVerb() {
    Set<Verb> verbs = DB.VERBS;

    if (schema.getTenses() != null) {
      verbs = verbs.stream()
                   .filter(v -> v.getConjugators().stream().anyMatch(c -> schema.getTenses().contains(c.getTense()))).collect(
              Collectors.toSet());
    }

    if (schema.getVerbType() != null) {
      verbs = verbs.stream().filter(v -> v.getVerbType() == schema.getVerbType()).collect(Collectors.toSet());
    }
    return verbs.stream().skip(RANDOM.nextInt(verbs.size()))
                .findFirst()
                .get();
  }

  private PossessiveWord getVerbConjugation(Verb verb, PossessiveWord subject, Lang lang) {
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

  private PossessiveWord getNoun() {
    Noun noun = DB.NOUNS.stream().skip(RANDOM.nextInt(DB.NOUNS.size())).findFirst().get();
    this.nounSubject = noun;
    return new PossessiveWord(noun.getWordBySingular(true));
  }

  private GenderedWord getAdjective(Adjective adjective, PossessiveWord subject, Lang lang) {
    if (subject == null) {
      System.err.println("null subject");
    }
    return adjective.getWordByGenderAndSingular(subject.getGender(lang), lang, subject.isSingular());
  }

  private Adjective getAbstractAdjective(PossessiveWord subject) {
    Set<NounType> nounTypes = new HashSet<>();
    if (subject instanceof PersonalPronoun) {
      nounTypes.add(NounType.PERSON);
    } else {
      nounTypes.addAll(nounSubject.getNounTypes());
    }
    Set<Adjective> adjectives = DB.ADJECTIVES;
    if (!nounTypes.isEmpty()) {
      adjectives = adjectives.stream().filter(a -> a.getPossibleNouns().stream()
                                                    .anyMatch(nounTypes::contains)).collect(Collectors.toSet());
    }
    Optional<Adjective> adjectiveOpt = adjectives.stream().skip(RANDOM.nextInt(adjectives.size())).findFirst();
    if (adjectiveOpt.isEmpty()) {
      System.err.println("adjective empty");
    }
    return adjectiveOpt.get();
  }


}
