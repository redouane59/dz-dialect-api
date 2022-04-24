package io.github.Redouane59.dz.model.sentence.V2;

import static io.github.Redouane59.dz.model.sentence.WordPicker.RANDOM;

import io.github.Redouane59.dz.function.GeneratorParameters;
import io.github.Redouane59.dz.model.Articles;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.PersonalPronouns;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import io.github.Redouane59.dz.model.word.Sentence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractSentenceBuilder {

  private SentenceSchema     schema;
  private PossessiveWord     subject;
  private Tense              tense;
  private List<AbstractWord> abstractWords = new ArrayList<>();

  public Optional<Sentence> generate(GeneratorParameters bodyArgs) {
    this.initAbstractWords();
    Sentence        sentence = new Sentence();
    Optional<Tense> tenseOpt = getRandomTenseFromSchema();
    if (tenseOpt.isEmpty()) {
      return Optional.empty();
    }
    tense = tenseOpt.get();
    Translation frTranslation = generateFrTranslation();
    Translation arTranslation = generateArTranslation(Lang.DZ);
    sentence.addTranslation(Lang.FR, frTranslation.getValue());
    sentence.addTranslation(Lang.DZ, arTranslation.getValue(), arTranslation.getArValue());
    return Optional.of(sentence);
  }

  public Translation getArticleTranslation(Lang lang) {
    return Articles.getArticleByCriterion(subject.getGender(lang), Possession.OTHER, subject.isSingular(), true)
                   .get()
                   .getTranslationByLang(lang)
                   .get();
  }

  public Translation getAdjectiveTranslation(Lang lang) {
    Adjective adjective = (Adjective) getAbstractWords().stream().filter(w -> w.getWordType() == WordType.ADJECTIVE).findFirst().get();
    return adjective.getTranslationByGender(getSubject().getGender(), getSubject().isSingular(), lang);
  }

  public Translation getPronounTranslation(Lang lang) {
    return PersonalPronouns.getPronounByCriterion(getSubject().getGender(), getSubject().getPossession(), getSubject().isSingular())
                           .get().getTranslationByLang(lang).get();
  }

  public Optional<Translation> getConjugationTranslation(Verb verb, Lang lang) {
    Optional<Conjugation>
        conjugation =
        verb.getConjugationByGenderSingularPossessionAndTense(getSubject().getGender(lang),
                                                              getSubject().isSingular(),
                                                              getSubject().getPossession(),
                                                              tense);
    if (conjugation.isEmpty()) {
      System.err.println("no conjugation found for " + verb.getId()
                         + " | gender:" + getSubject().getGender(lang)
                         + " | singular " + getSubject().isSingular()
                         + " | possession " + getSubject().getPossession()
                         + " | tense " + tense
      );
      return Optional.empty();
    }
    return conjugation.get().getTranslationByLang(lang);
  }

  public Optional<Tense> getRandomTenseFromSchema() {
    Optional<AbstractWord> verb = getAbstractWords().stream().filter(w -> w.getWordType() == WordType.VERB).findFirst();
    if (verb.isEmpty()) {
      return Optional.empty();
    }
    List<Tense> verbTenses = ((Verb) verb.get()).getConjugators().stream().map(Conjugator::getTense).collect(Collectors.toList());
    List<Tense> commonTenses = schema.getTenses().stream()
                                     .filter(verbTenses::contains).collect(Collectors.toList());
    if (commonTenses.isEmpty()) {
      return Optional.empty();
    }
    return commonTenses.stream().skip(RANDOM.nextInt(commonTenses.size())).findFirst();
  }

  public abstract void initAbstractWords();

  public abstract Translation generateFrTranslation();

  public abstract Translation generateArTranslation(Lang lang);

  public boolean isCompatible(final GeneratorParameters bodyArgs) {
    return true; // @todo to implement
  }
}
