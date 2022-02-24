package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Verb extends AbstractWord {

  private List<Conjugator> conjugators;
  @JsonProperty("possible_complements")
  private List<WordType>   possibleComplements;
  @JsonProperty("verb_type")
  private VerbType         verbType;

  public Optional<Conjugation> getRandomConjugation(List<Tense> tenses) {
    // get all possible conjugation
    List<Conjugator> matchingConjugator = conjugators.stream().filter(o -> tenses.contains(o.getTense())).collect(Collectors.toList());
    if (matchingConjugator.isEmpty()) {
      return Optional.empty();
    }
    int        index      = new Random().nextInt(matchingConjugator.size());
    Conjugator conjugator = matchingConjugator.get(index);
    index = new Random().nextInt(conjugator.getConjugations().size());
    return Optional.of(conjugator.getConjugations().get(index));
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

    if (!Config.DISPLAY_STATE_VERB.contains(lang)) {
      return "";
    }
    Optional<Conjugator> conjugator = conjugators.stream()
                                                 .filter(o -> o.getTense() == tense).findAny();
    if (conjugator.isEmpty()) {
      return "";
    }

    Optional<Conjugation> conjugation = conjugator.get().getConjugations().stream()
                                                  .filter(o -> o.isSingular() == singular)
                                                  .filter(o -> o.getGender() == gender || gender == Gender.X || (gender == Gender.F && !singular))
                                                  .filter(o -> o.getPossession() == Possession.OTHER)
                                                  .findAny();

    if (conjugation.isPresent()) {
      return conjugation.get().getTranslationValue(lang);
    }
    return "";
  }
}
