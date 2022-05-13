package io.github.Redouane59.dz.model.verb;

import static io.github.Redouane59.dz.model.sentence.SentenceBuilder.RANDOM;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @todo switch to enum
public class PersonalPronouns {

  @Getter
  private static final List<PersonalPronoun> personalPronouns = new ArrayList<>();

  public PersonalPronouns(String filePath) {
    try {
      File       file           = new File(filePath);
      JsonNode[] objectSuffixes = Config.OBJECT_MAPPER.readValue(file, JsonNode[].class);
      for (JsonNode jsonNode : objectSuffixes) {
        PersonalPronoun pronoun = new PersonalPronoun();
        pronoun.setGender(Gender.valueOf(jsonNode.get("gender").asText()));
        pronoun.setPossession(Possession.valueOf(jsonNode.get("possession").asText()));
        pronoun.setSingular(jsonNode.get("singular").asBoolean());
        pronoun.setIndex(jsonNode.get("index").asInt());
        pronoun.setTranslations(Config.OBJECT_MAPPER.readValue(jsonNode.get("translations").toString(), new TypeReference<>() {
        }));
        personalPronouns.add(pronoun);
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static Optional<PersonalPronoun> getPronounByCriterion(Gender gender, Possession possession, boolean singular) {
    return personalPronouns.stream()
                           .filter(a -> a.getPossession() == possession)
                           .filter(a -> a.isSingular() == singular)
                           .filter(a -> a.getGender() == gender || a.getGender() == Gender.X || gender == Gender.X)
                           .findAny();
  }

  public static PersonalPronoun getRandomPersonalPronoun() {
    return getRandomPersonalPronoun(false);
  }

  public static PersonalPronoun getRandomPersonalPronoun(boolean isObject) {
    if (isObject) {
      List<PersonalPronoun> objectPronoun = personalPronouns.stream()
                                                            .filter(o -> o.getPossession() == Possession.OTHER).collect(Collectors.toList());
      return objectPronoun.stream().skip(RANDOM.nextInt(objectPronoun.size())).findFirst().get();
    } else {
      return personalPronouns.stream().skip(RANDOM.nextInt(personalPronouns.size())).findFirst().get();
    }
  }

  public static PersonalPronoun getRandomImperativePersonalPronoun() {
    List<PersonalPronoun> objectPronoun = personalPronouns.stream()
                                                          .filter(o -> o.getPossession() == Possession.YOU).collect(Collectors.toList());
    return objectPronoun.stream().skip(RANDOM.nextInt(objectPronoun.size())).findFirst().get();

  }

  public static PersonalPronoun getPersonalPronounByValue(String frValue, String dzValue) {
    return personalPronouns.stream()
                           .filter(o -> o.getTranslationValue(Lang.FR).equals(frValue))
                           .filter(o -> o.getTranslationValue(Lang.DZ).equals(dzValue))
                           .findFirst().orElseThrow();
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class PersonalPronoun extends PossessiveWord {

    private int index;
  }

}
