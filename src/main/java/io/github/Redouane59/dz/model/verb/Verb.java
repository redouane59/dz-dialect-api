package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
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
  private             Set<Conjugator> conjugators         = new HashSet<>();
  @JsonProperty("possible_questions")
  private             Set<Question>   possibleQuestions   = new HashSet<>();
  @JsonProperty("possible_complements")
  private             Set<NounType>   possibleComplements = new HashSet<>(); // @todo add verbs
  @JsonProperty("verb_type")
  private             VerbType        verbType;
  @JsonProperty("indirect_complement")
  private             boolean         indirectComplement; // ex: je LUI donne quelque chose.
  @JsonProperty("direct_complement")
  private             boolean         directComplement; // ex: je LE donne.
  @JsonProperty("dz_opposite_complement")
  private             boolean         dzOppositeComplement; // ex: je l'appelle / n3ayetlou
  @JsonProperty("object_only")
  private             boolean         objectOnly; // true : verbe ouvrir
  @JsonProperty("dz_no_suffix")
  private             boolean         dzNoSuffix;
  @JsonProperty("semi_auxiliar")
  private             boolean         semiAuxiliar; // conjugated verb + infinitive verb

  public static Set<Verb> deserializeFromCSV(String fileName, boolean removeHeader) {
    List<List<String>> entries               = FileHelper.getCsv(Verb.class.getClassLoader().getResource(fileName).getPath(), ",", removeHeader);
    int                verbInfinitiveIndex   = 0;
    int                tenseIndex            = 1;
    int                personalPronounsIndex = 2;
    String             pronounDelimiter      = "/";
    int                frValueIndex          = 3;
    int                dzValueIndex          = 4;
    int                dzValueArIndex        = 5;
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
      verb.setVerbType(VerbType.ACTION);

      try {
        Tense tense = Tense.valueOf(values.get(tenseIndex));
        PersonalProunoun
            personalProunoun =
            PersonalProunoun.getPersonalPronounByValue(values.get(personalPronounsIndex).split(pronounDelimiter)[0],
                                                       values.get(personalPronounsIndex).split(pronounDelimiter)[1]);
        String frValue   = values.get(frValueIndex);
        String dzValue   = values.get(dzValueIndex);
        String dzValueAr = null;
        if (values.size() > dzValueArIndex) {
          dzValueAr = values.get(dzValueArIndex);
        }
        Conjugation conjugation = new Conjugation();
        if (personalProunoun.getPossession() == Possession.YOU || (personalProunoun.getPossession() == Possession.OTHER
                                                                   && personalProunoun.isSingular())) {
          conjugation.setGender(personalProunoun.getGender());
        } else {
          conjugation.setGender(Gender.X); // @todo to improve
        }
        conjugation.setPossession(personalProunoun.getPossession());
        conjugation.setSingular(personalProunoun.isSingular());
        conjugation.setTranslations(List.of(new Translation(Lang.FR, frValue),
                                            new Translation(Lang.DZ, dzValue, dzValueAr)));
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

  // to manage the fact that two files are needed to import a verb (see DB.java)
  public void importConfig(Verb other) {
    this.possibleQuestions    = other.getPossibleQuestions();
    this.possibleComplements  = other.getPossibleComplements();
    this.verbType             = other.getVerbType();
    this.indirectComplement   = other.isIndirectComplement();
    this.directComplement     = other.isDirectComplement();
    this.dzOppositeComplement = other.isDzOppositeComplement();
    this.objectOnly           = other.isObjectOnly();
    this.dzNoSuffix           = other.isDzNoSuffix();
    this.semiAuxiliar         = other.isSemiAuxiliar();
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

}
