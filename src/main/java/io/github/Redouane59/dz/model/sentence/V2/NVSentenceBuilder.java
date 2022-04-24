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
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class NVSentenceBuilder extends AbstractSentenceBuilder {

  public NVSentenceBuilder() {
    try {
      setSchema(OBJECT_MAPPER.readValue(new File("src/main/resources/sentences/nv_sentence.json"), SentenceSchema.class));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Translation generateFrTranslation() {
    String sentenceValue = "";
    sentenceValue = getArticleTranslation(Lang.FR).getValue();
    sentenceValue += " ";
    sentenceValue += getSubject().getTranslationValue(Lang.FR);
    sentenceValue += " ";
    // sentenceValue += getConjugationTranslation(Lang.FR).orElse(new Translation(Lang.FR, "")).getValue();
    return new Translation(Lang.FR, sentenceValue);
  }

  public Translation generateArTranslation(Lang lang) {
    Translation articleTranslation = getArticleTranslation(Lang.DZ);
    String      value              = articleTranslation.getValue();
    String      arValue            = articleTranslation.getArValue();

    value += " ";
    value += getSubject().getTranslationValue(lang);
    value += " ";

    arValue += " ";
    arValue += getSubject().getTranslationByLang(lang).get().getArValue();
    arValue += " ";
/*    Translation conjugationTranslation = getConjugationTranslation(Lang.DZ).orElse(new Translation(lang, "", ""));
    value += conjugationTranslation.getValue();

    arValue += " ";
    arValue += conjugationTranslation.getArValue();*/
    return new Translation(Lang.FR, value, arValue);
  }


  public void initAbstractWords() {
    setAbstractWords(new ArrayList<>());

    for (WordType wordType : getSchema().getFrSequence()) {
      getAbstractWords().add(getRandomWordByType(wordType));
    }
    GenderedWord   gSubject = getAbstractWords().get(getSchema().getSubjectPosition()).getWordBySingular(RANDOM.nextBoolean());
    PossessiveWord subject  = new PossessiveWord(Possession.OTHER);
    subject.setGender(gSubject.getGender());
    subject.setSingular(gSubject.isSingular());
    subject.setTranslations(gSubject.getTranslations());
    setSubject(subject);
  }

  public AbstractWord getRandomWordByType(WordType wordType) {
    switch (wordType) {
      case NOUN:
        Set<Noun> personNouns = DB.NOUNS.stream().filter(n -> n.getNounTypes().contains(NounType.PERSON)).collect(Collectors.toSet());
        return personNouns.stream().skip(RANDOM.nextInt(personNouns.size())).findFirst().get();
      case VERB:
        Set<Verb> verbs = DB.VERBS.stream()
                                  .filter(v -> v.getConjugators().stream()
                                                .anyMatch(c -> c.getConjugations().stream()
                                                                .anyMatch(x -> x.getDzTranslationAr() != null))).collect(Collectors.toSet());
        return verbs.stream().skip(RANDOM.nextInt(verbs.size())).findFirst().get();
      default:
        AbstractWord abstractWord = new AbstractWord();
        abstractWord.setWordType(wordType);
        return abstractWord;
    }
  }


}
