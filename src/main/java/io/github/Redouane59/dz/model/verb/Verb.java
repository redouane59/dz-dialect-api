package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Question;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Verb extends AbstractWord {

  @JsonProperty("possible_questions")
  private final List<Question>   possibleQuestions   = new ArrayList<>();
  private final List<Conjugator> conjugators         = new ArrayList<>();
  @JsonProperty("possible_complements")
  private final List<WordType>   possibleComplements = new ArrayList<>();
  @JsonProperty("verb_type")
  private       VerbType         verbType;

  public Optional<Conjugator> getRandomConjugator(List<Tense> tenses) {
    List<Conjugator> matchingConjugator = conjugators.stream().filter(o -> tenses.contains(o.getTense())).collect(Collectors.toList());
    if (matchingConjugator.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingConjugator.get(new Random().nextInt(matchingConjugator.size())));
  }

  public Optional<Conjugation> getRandomConjugation(Conjugator conjugator) {
    return Optional.of(conjugator.getConjugations().get(new Random().nextInt(conjugator.getConjugations().size())));
  }

  public Optional<Conjugation> getRandomConjugationByTenses(List<Tense> tenses) {
    Conjugator conjugator = getRandomConjugator(tenses).get();
    return Optional.of(conjugator.getConjugations().get(new Random().nextInt(conjugator.getConjugations().size())));
  }

  public Optional<Conjugator> getConjugationByTense(Tense tense) {
    return conjugators.stream().filter(o -> o.getTense() == tense).findAny();
  }

  public Optional<Conjugation> getConjugationByGenderSingularAndTense(Gender gender, boolean isSingular, Tense tense) {
    Optional<Conjugator> conjugator = conjugators.stream().filter(o -> o.getTense() == tense)
                                                 .findAny();
    if (conjugator.isEmpty()) {
      return Optional.empty();
    }
    return conjugator.get().getConjugationByCriteria(gender, isSingular);
  }

  public String getNounConjugation(Gender gender, boolean singular, Tense tense, Lang lang) {

    if (!Config.DISPLAY_STATE_VERB.contains(lang) && tense == Tense.PRESENT) {
      return "";
    }
    Optional<Conjugator> conjugator = conjugators.stream()
                                                 .filter(o -> o.getTense() == tense).findAny();
    if (conjugator.isEmpty()) {
      return "";
    }

    Optional<Conjugation> conjugation = conjugator.get().getConjugations().stream()
                                                  .filter(o -> o.isSingular() == singular)
                                                  .filter(o -> o.getGender() == gender || gender == Gender.X || !singular)
                                                  .filter(o -> o.getPossession() == Possession.OTHER)
                                                  .findAny();

    if (conjugation.isPresent()) {
      return conjugation.get().getTranslationValue(lang);
    }
    return "";
  }
}
