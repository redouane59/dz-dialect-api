package io.github.Redouane59.dz.model.sentence.V2;

import static io.github.Redouane59.dz.helper.Config.OBJECT_MAPPER;
import static io.github.Redouane59.dz.model.sentence.WordPicker.RANDOM;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.verb.PersonalPronouns;
import io.github.Redouane59.dz.model.verb.PersonalPronouns.PersonalPronoun;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GenericSentenceBuilder extends AbstractSentenceBuilder {

  public GenericSentenceBuilder(String filePath) {
    try {
      setSchema(OBJECT_MAPPER.readValue(new File(filePath), SentenceSchema.class));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Translation generateFrTranslation() {
    StringBuilder sentenceValue = new StringBuilder();
    for (AbstractWord word : getAbstractWords()) {
      switch (word.getWordType()) {
        case PRONOUN:
          if (getSubject() instanceof PersonalPronoun) {
            sentenceValue.append(getSubject().getTranslationByLang(Lang.FR).get().getValue());
          } else {
            sentenceValue.append(getPronounTranslation(Lang.FR).getValue());
          }
          break;
        case NOUN:
          sentenceValue.append(getSubject().getTranslationValue(Lang.FR));
          break;
        case VERB:
          Optional<Translation> conjugation = getConjugationTranslation((Verb) word, Lang.FR);
          conjugation.ifPresent(translation -> sentenceValue.append(translation.getValue()));
          break;
        case ARTICLE:
          sentenceValue.append(getArticleTranslation(Lang.FR).getValue());
          break;
        case ADJECTIVE:
          sentenceValue.append(getAdjectiveTranslation(Lang.FR).getValue());
          break;
      }
      sentenceValue.append(" ");
    }
    return new Translation(Lang.FR, sentenceValue.toString());
  }

  public Translation generateArTranslation(Lang lang) {
    StringBuilder sentenceValue   = new StringBuilder();
    StringBuilder sentenceValueAr = new StringBuilder();
    for (AbstractWord word : getAbstractWords()) {
      switch (word.getWordType()) {
        case PRONOUN:
          break;
        case NOUN:
          sentenceValue.append(getSubject().getTranslationByLang(Lang.DZ).get().getValue());
          sentenceValueAr.append(getSubject().getTranslationByLang(Lang.DZ).get().getArValue());
          break;
        case VERB:
          Optional<Translation> conjugation = getConjugationTranslation((Verb) word, Lang.DZ);
          conjugation.ifPresent(translation -> sentenceValue.append(translation.getValue()));
          conjugation.ifPresent(translation -> sentenceValueAr.append(translation.getArValue()));
          break;
        case ARTICLE:
          sentenceValue.append(getArticleTranslation(Lang.DZ).getValue());
          sentenceValueAr.append(getArticleTranslation(Lang.DZ).getArValue());
          break;
        case ADJECTIVE:
          sentenceValue.append(getAdjectiveTranslation(Lang.DZ).getValue());
          sentenceValueAr.append(getAdjectiveTranslation(Lang.DZ).getArValue());
          break;
      }
      sentenceValue.append(" ");
      sentenceValueAr.append(" ");
    }
    return new Translation(Lang.FR, sentenceValue.toString(), sentenceValueAr.toString());
  }


  public void initAbstractWords() {
    setAbstractWords(new ArrayList<>());
    for (WordType wordType : getSchema().getFrSequence()) {
      AbstractWord abstractWord = getRandomWordByType(wordType);
      getAbstractWords().add(abstractWord);
    }
    AbstractWord sWord = getAbstractWords().get(getSchema().getSubjectPosition());
    if (sWord.getWordType() == WordType.NOUN) {
      GenderedWord   gSubject = getAbstractWords().get(getSchema().getSubjectPosition()).getWordBySingular(RANDOM.nextBoolean());
      PossessiveWord subject  = new PossessiveWord(Possession.OTHER);
      subject.setGender(gSubject.getGender());
      subject.setSingular(gSubject.isSingular());
      subject.setTranslations(gSubject.getTranslations());
      setSubject(subject);
    } else if (sWord.getWordType() == WordType.PRONOUN) {
      setSubject(PersonalPronouns.getRandomPersonalPronoun());
    } else {
      setSubject(PersonalPronouns.getRandomPersonalPronoun(true));
    }
  }

  public AbstractWord getRandomWordByType(WordType wordType) {
    switch (wordType) {
      case NOUN:
        Set<Noun> personNouns = DB.NOUNS.stream().filter(n -> n.getNounTypes().contains(NounType.PERSON)).collect(Collectors.toSet());
        return personNouns.stream().skip(RANDOM.nextInt(personNouns.size())).findFirst().get();
      case VERB:
        // verbs with arabic
        Set<Verb> verbs = DB.VERBS.stream()
                                  .filter(v -> v.getConjugators().stream()
                                                .anyMatch(c -> c.getConjugations().stream()
                                                                .anyMatch(x -> x.getDzTranslationAr() != null))).collect(Collectors.toSet());
        // verbs with compatible tense
        verbs = verbs.stream().filter(v -> v.getConjugators().stream()
                                            .anyMatch(c -> getSchema().getTenses().contains(c.getTense()))).collect(Collectors.toSet());
        // verbs with type if exists
        if (getSchema().getVerbType() != null) {
          verbs = verbs.stream().filter(v -> v.getVerbType() == getSchema().getVerbType()).collect(Collectors.toSet());
        }
        return verbs.stream().skip(RANDOM.nextInt(verbs.size())).findFirst().get();
      case ADJECTIVE:
        return DB.ADJECTIVES.stream().skip(RANDOM.nextInt(DB.ADJECTIVES.size())).findFirst().get();
      default:
        AbstractWord abstractWord = new AbstractWord();
        abstractWord.setWordType(wordType);
        return abstractWord;
    }
  }


}
