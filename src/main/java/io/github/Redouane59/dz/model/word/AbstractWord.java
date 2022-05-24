package io.github.Redouane59.dz.model.word;

import static io.github.Redouane59.dz.model.sentence.SentenceBuilder.RANDOM;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AbstractWord {

  @JsonInclude(Include.NON_EMPTY)
  @JsonDeserialize(using = ConjugationListDeserializer.class)
  private List<? super Word> values = new ArrayList<>(); // @todo fix it
  private String             id;
  @JsonProperty("word_type")
  private WordType           wordType;

  @JsonIgnore
  public static Optional<PossessiveWord> getPersonalPronoun(Gender gender, boolean isSingular, Possession possession) {
    Optional<PossessiveWord> result = DB.PERSONAL_PRONOUNS.stream()
                                                          .map(o -> o.getValues().get(0))//@todo dirty ?
                                                          .map(o -> (PossessiveWord) o)
                                                          .filter(o -> o.isSingular() == isSingular)
                                                          .filter(o -> o.getPossession() == possession)
                                                          .filter(o -> o.getGender() == gender || gender == Gender.X || o.getGender() == Gender.X)
                                                          .findAny();
    return result;

  }

  @JsonIgnore
  public static PossessiveWord getRandomImperativePersonalPronoun() {
    List<PossessiveWord> compatiblePronouns = DB.PERSONAL_PRONOUNS.stream()
                                                                  .map(o -> o.getValues().get(0))
                                                                  .map(o -> (PossessiveWord) o)
                                                                  .filter(o -> o.getPossession() == Possession.YOU).collect(Collectors.toList());
    return compatiblePronouns.stream().skip(RANDOM.nextInt(compatiblePronouns.size()))
                             .findAny().get();
  }

  // @todo not the right place ?
  @JsonIgnore
  public static Optional<GenderedWord> getDefinedArticleByCriterion(Gender gender, boolean singular) {
    return DB.DEFINED_ARTICLES.getValues().stream()
                              .map(o -> (GenderedWord) o)
                              .filter(a -> a.isSingular() == singular)
                              .filter(a -> a.getGender() == gender/* || a.getGender() == Gender.X || gender == Gender.X*/)
                              .findAny();
  }

  @JsonIgnore
  public static PossessiveWord getOppositeSuffix(PossessiveWord suffix) {
    if (DB.INDIRECT_SUFFIXES.getValues().contains(suffix)) {
      return DB.DIRECT_SUFFIXES.getValues().stream()
                               .map(o -> (PossessiveWord) o)
                               .filter(s -> s.isSingular() == suffix.isSingular())
                               .filter(s -> s.getPossession() == suffix.getPossession())
                               .filter(s -> s.getGender() == suffix.getGender())
                               .findFirst().get();
    } else {
      return DB.INDIRECT_SUFFIXES.getValues().stream()
                                 .map(o -> (PossessiveWord) o)
                                 .filter(s -> s.isSingular() == suffix.isSingular())
                                 .filter(s -> s.getPossession() == suffix.getPossession())
                                 .filter(s -> s.getGender() == suffix.getGender())
                                 .findFirst().get();
    }
  }

  @JsonIgnore
  public String getTranslationValueByGender(Gender gender, boolean isSingular, Lang lang) {
    return getTranslationByGender(gender, isSingular, lang).getValue();
  }

  @JsonIgnore
  public Translation getTranslationByGender(Gender gender, boolean isSingular, Lang lang) {
    return getValues().stream()
                      .map(o -> (GenderedWord) o)
                      .filter(o -> o.isSingular() == isSingular)
                      .filter(o -> (o.getGender(lang) == gender || gender == Gender.X || o.getGender(lang) == Gender.X))
                      .map(o -> o.getTranslationByLang(lang).get())
                      .findAny()
                      .orElse(new Translation(lang, ""));
  }

  @JsonIgnore
  public GenderedWord getWordBySingular(boolean isSingular) {
    return getValues().stream()
                      .map(o -> (GenderedWord) o)
                      .filter(o -> o.isSingular() == isSingular)
                      .findAny()
                      .orElseThrow();
  }

  @JsonIgnore
  public Optional<? extends GenderedWord> getWordByGenderAndSingular(Gender gender, Lang lang, boolean isSingular) {
    Optional<? extends GenderedWord> result = getValues().stream()
                                                         .map(o -> (GenderedWord) o)
                                                         .filter(o -> o.getGender(lang) == gender
                                                                      || o.getGender(lang) == Gender.X
                                                                      || gender == Gender.X)
                                                         .filter(o -> o.isSingular() == isSingular)
                                                         .findAny();
    if (result.isEmpty()) {
      System.err.println("no word found");
      return Optional.empty();
    }
    return result;
  }

  @JsonIgnore
  public Optional<Conjugation> getConjugationByGenderSingularAndPossession(Gender gender, boolean isSingular, Possession possession, Lang lang) {
    return getValues().stream()
                      .map(o -> (Conjugation) o)
                      .filter(o -> o.isSingular() == isSingular)
                      .filter(o -> o.getPossession() == possession)
                      .filter(o -> o.getGender(lang) == gender || o.getGender(lang) == Gender.X).findAny();

  }

  @JsonIgnore
  public Optional<Translation> getTranslationBySingular(boolean isSingular, Lang lang) {
    return getValues().stream()
                      .map(o -> (Conjugation) o)
                      .filter(o -> o.isSingular() == isSingular)
                      .map(o -> o.getTranslationByLang(lang).get())
                      .findAny();
  }

  public String getDzValue(Gender gender, boolean isSingular) {
    return this.getTranslationByGender(gender, isSingular, Lang.DZ).getValue();
  }

  public String getFrValue(Gender gender, boolean isSingular) {
    return this.getTranslationByGender(gender, isSingular, Lang.FR).getValue();
  }

  @JsonIgnore
  public Optional<PossessiveWord> getPersonalPronounByValue(String frValue, String dzValue) {
    return DB.PERSONAL_PRONOUNS.stream()
                               .map(p -> p.getValues().get(0))
                               .map(o -> (PossessiveWord) o)
                               .filter(o -> o.getTranslationValue(Lang.FR).contains(frValue))
                               .filter(o -> o.getTranslationValue(Lang.DZ).contains(dzValue))
                               .findFirst();
  }

  @JsonIgnore
  public PossessiveWord getRandomConjugation() {
    if (getValues().isEmpty()) {
      System.err.println("no values found");
      return null;
    }
    return getValues().stream().map(o -> (PossessiveWord) o)
                      .skip(RANDOM.nextInt(getValues().size())).findFirst().get();
  }
}
