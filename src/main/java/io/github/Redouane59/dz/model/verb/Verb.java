package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.helper.FileHelper;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.noun.NounType;
import io.github.Redouane59.dz.model.question.Question;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Word;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Verb extends AbstractWord {

  @JsonIgnore
  public static final ObjectMapper
                                      OBJECT_MAPPER       =
      new ObjectMapper().registerModule(new SimpleModule().addSerializer(Word.class, new WordFromCSVSerializer()));
  @JsonProperty("possible_questions")
  private             Set<Question>   possibleQuestions   = new HashSet<>();
  private             Set<Conjugator> conjugators         = new HashSet<>();
  @JsonProperty("possible_complements")
  private             Set<NounType>   possibleComplements = new HashSet<>();
  @JsonProperty("verb_type")
  private             VerbType        verbType;
  @JsonProperty("reflexive_suffix_fr")
  private             ReflexiveSuffix reflexiveSuffixFr;
  @JsonProperty("reflexive_suffix_dz")
  private             ReflexiveSuffix reflexiveSuffixDz;

  public static Set<Verb> deserializeFromCSV(String fileName, boolean removeHeader) {
    List<List<String>> entries               = FileHelper.getCsv(Verb.class.getClassLoader().getResource(fileName).getPath(), ",", removeHeader);
    int                verbInfinitiveIndex   = 0;
    int                tenseIndex            = 1;
    int                personalPronounsIndex = 2;
    String             pronounDelimiter      = "/";
    int                frValueIndex          = 3;
    int                dzValueIndex          = 4;
    Set<Verb>          verbs                 = new HashSet<>();

    for (List<String> values : entries) {
      Verb           verb    = new Verb();
      Optional<Verb> verbOpt = verbs.stream().filter(o -> o.getId().equals(values.get(verbInfinitiveIndex))).findFirst();
      if (verbOpt.isEmpty()) {
        verb.setId(values.get(verbInfinitiveIndex));
        verbs.add(verb);
      } else {
        verb = verbOpt.get();
      }
      verb.setWordType(WordType.VERB);

      try {
        Tense tense = Tense.valueOf(values.get(tenseIndex));
        PersonalProunoun
            personalProunoun =
            PersonalProunoun.getPersonalPronounByValue(values.get(personalPronounsIndex).split(pronounDelimiter)[0],
                                                       values.get(personalPronounsIndex).split(pronounDelimiter)[1]);
        String      frValue     = values.get(frValueIndex); // @todo jackson change deserialization call
        String      dzValue     = values.get(dzValueIndex);
        Conjugation conjugation = new Conjugation();
        conjugation.setGender(personalProunoun.getGender());
        conjugation.setPossession(personalProunoun.getPossession());
        conjugation.setSingular(personalProunoun.isSingular());
        conjugation.setTranslations(List.of(new Translation(Lang.FR, frValue), new Translation(Lang.DZ, dzValue)));
        Optional<Conjugator> conjugatorOpt = verb.getConjugators().stream().filter(o -> o.getTense() == tense).findFirst();
        Conjugator           conjugator;
        if (conjugatorOpt.isEmpty()) {
          conjugator = new Conjugator();
          conjugator.setTense(tense);
          verb.getConjugators().add(conjugator);
        } else {
          conjugator = conjugatorOpt.get();
        }
        conjugator.getConjugations().add(conjugation);
      } catch (Exception ignored) {
      }
    }
    return verbs;
  }

  public Optional<Conjugator> getRandomConjugator(Set<Tense> tenses) {
    List<Conjugator> matchingConjugator = conjugators.stream().filter(o -> tenses.contains(o.getTense())).collect(Collectors.toList());
    if (matchingConjugator.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(matchingConjugator.get(new Random().nextInt(matchingConjugator.size())));
  }

  public Optional<Conjugation> getRandomConjugation(Conjugator conjugator) {
    return conjugator.getConjugations().stream().skip(new Random().nextInt(conjugator.getConjugations().size())).findFirst();
  }

  public Optional<Conjugation> getRandomConjugationByTenses(Set<Tense> tenses) {
    Conjugator conjugator = getRandomConjugator(tenses).get();
    return conjugator.getConjugations().stream().skip(new Random().nextInt(conjugator.getConjugations().size())).findFirst();
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
