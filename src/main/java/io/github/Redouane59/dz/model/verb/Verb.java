package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.Redouane59.dz.helper.FileHelper;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.NounType;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import io.github.Redouane59.dz.model.word.PossessiveWord;
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
@JsonInclude(Include.NON_DEFAULT)
// @todo reflexive verbs (ça me plaît, il me faut, etc.)
public class Verb extends AbstractWord {

  @JsonIgnore
  public static final ObjectMapper
                                    OBJECT_MAPPER       =
      new ObjectMapper().registerModule(new SimpleModule().addSerializer(Word.class, new WordFromCSVSerializer()));
  @JsonProperty("possible_questions")
  private             Set<String>   possibleQuestionIds = new HashSet<>();
  @JsonProperty("possible_complements")
  private             Set<NounType> possibleComplements = new HashSet<>(); // @todo add verbs for PVV/NVV sentences
  @JsonProperty("verb_type")
  private             VerbType      verbType;
  @JsonProperty("indirect_complement")
  private             boolean       indirectComplement; // ex: je LUI donne quelque chose.
  @JsonProperty("direct_complement")
  private             boolean       directComplement; // ex: je LE donne.
  @JsonProperty("dz_opposite_complement")
  private             boolean       dzOppositeComplement; // ex: je l'appelle / n3ayetlou
  @JsonProperty("object_only")
  private             boolean       objectOnly; // true : verbe ouvrir
  @JsonProperty("dz_no_suffix")
  private             boolean       dzNoSuffix;
  @JsonProperty("semi_auxiliar")
  private             boolean       semiAuxiliar; // conjugated verb + infinitive verb
  private             WordType      wordType            = WordType.VERB;

  public Set<Verb> deserializeFromCSV(String fileName, boolean removeHeader) {
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

      try {
        Tense tense = Tense.valueOf(values.get(tenseIndex));
        PossessiveWord
            personalProunoun =
            getPersonalPronounByValue(values.get(personalPronounsIndex).split(pronounDelimiter)[0],
                                      values.get(personalPronounsIndex).split(pronounDelimiter)[1]).get();
        String frValue   = values.get(frValueIndex);
        String dzValue   = values.get(dzValueIndex);
        String dzValueAr = null;
        if (values.size() > dzValueArIndex) {
          dzValueAr = values.get(dzValueArIndex);
        }
        Conjugation conjugation = new Conjugation();
        if ((personalProunoun.getPossession() == Possession.YOU && personalProunoun.isSingular()) || (personalProunoun.getPossession()
                                                                                                      == Possession.OTHER
                                                                                                      && personalProunoun.isSingular())) {
          conjugation.setGender(personalProunoun.getGender());
        } else {
          conjugation.setGender(Gender.X); // @todo to improve
        }
        conjugation.setPossession(personalProunoun.getPossession());
        conjugation.setSingular(personalProunoun.isSingular());
        conjugation.setTense(tense);
        conjugation.setTranslations(Set.of(new Translation(Lang.FR, frValue),
                                           new Translation(Lang.DZ, dzValue, dzValueAr)));
        verb.getValues().add(conjugation);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    return verbs;
  }

  // to manage the fact that two files are needed to import a verb (see DB.java)
  public void importConfig(Verb other) {
    this.possibleQuestionIds  = other.getPossibleQuestionIds();
    this.possibleComplements  = other.getPossibleComplements();
    this.verbType             = other.getVerbType();
    this.indirectComplement   = other.isIndirectComplement();
    this.directComplement     = other.isDirectComplement();
    this.dzOppositeComplement = other.isDzOppositeComplement();
    this.objectOnly           = other.isObjectOnly();
    this.dzNoSuffix           = other.isDzNoSuffix();
    this.semiAuxiliar         = other.isSemiAuxiliar();
  }

  public Optional<Conjugation> getRandomConjugationByTenses(Set<Tense> tenses) {
    List<Conjugation> matchingConjugation = getValues().stream().map(o -> (Conjugation) o)
                                                       .filter(o -> tenses.contains(o.getTense())).collect(Collectors.toList());
    return matchingConjugation.stream().skip(new Random().nextInt(matchingConjugation.size())).findFirst();
  }

  public Optional<Conjugation> getConjugationByTense(Tense tense) {
    return getValues().stream().map(o -> (Conjugation) o)
                      .filter(o -> o.getTense() == tense).findAny();
  }

  public Optional<Conjugation> getConjugationByGenderSingularPossessionAndTense(Gender gender,
                                                                                boolean isSingular,
                                                                                Possession possession,
                                                                                Tense tense) {
    Optional<Conjugation> result = getValues().stream().map(o -> (Conjugation) o)
                                              .filter(o -> o.getTense() == tense)
                                              .filter(o -> o.isSingular() == isSingular)
                                              .filter(o -> o.getPossession() == possession)
                                              .filter(o -> o.getGender() == gender || gender == Gender.X || o.getGender() == Gender.X)
                                              .findAny();
    return result;

  }

}
