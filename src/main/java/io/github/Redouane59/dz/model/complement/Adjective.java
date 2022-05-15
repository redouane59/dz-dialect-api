package io.github.Redouane59.dz.model.complement;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Redouane59.dz.helper.FileHelper;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Adjective extends AbstractWord {

  // @todo split in two as Verbs for translation & config
  @JsonProperty("possible_nouns")
  Set<NounType> possibleNouns;
  private boolean temporal;
  private boolean definitive;

  public static Set<Adjective> deserializeFromCSV(final String fileName, final boolean removeHeader) {
    List<List<String>> entries          = FileHelper.getCsv(Adjective.class.getClassLoader().getResource(fileName).getPath(), ",", removeHeader);
    int                adjectiveIdIndex = 0;
    int                singularIndex    = 1;
    int                genderIndex      = 2;
    int                frValueIndex     = 3;
    int                dzValueIndex     = 4;
    int                dzValueArIndex   = 5;
    Set<Adjective>     adjectives       = new HashSet<>();

    for (List<String> values : entries) {
      Adjective           abstractAdjective = new Adjective();
      Optional<Adjective> adjectiveOpt      = adjectives.stream().filter(o -> o.getId().equals(values.get(adjectiveIdIndex))).findFirst();
      if (adjectiveOpt.isEmpty()) { // new adjective
        abstractAdjective.setId(values.get(adjectiveIdIndex));
        adjectives.add(abstractAdjective);
      } else { // existing adjective
        abstractAdjective = adjectiveOpt.get();
      }

      try {

        boolean singular  = Boolean.parseBoolean(values.get(singularIndex));
        Gender  gender    = Gender.valueOf(values.get(genderIndex));
        String  frValue   = values.get(frValueIndex);
        String  dzValue   = values.get(dzValueIndex);
        String  dzValueAr = null;
        if (values.size() > dzValueArIndex) {
          dzValueAr = values.get(dzValueArIndex);
        }

        Conjugation adjective = new Conjugation();
        adjective.setSingular(singular);
        adjective.setGender(gender);
        adjective.setTranslations(Set.of(new Translation(Lang.FR, frValue),
                                         new Translation(Lang.DZ, dzValue, dzValueAr)));
        abstractAdjective.getValues().add(adjective);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return adjectives;
  }
}
