package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonalProunoun {

  I("1XS"),
  YOU_M("2MS"),
  YOU_F("2FS"),
  HE("3MS"),
  SHE("3FS"),
  WE("1XP"),
  YOU_P("2XP"),
  THEY_M("3MP"),
  THEY_F("3FP"),
  UNDEFINED("UNDEFINED");

  private final String            id;
  private       Possession        possession;
  private       boolean           singular;
  private       Gender            gender;
  private       List<Translation> translations;

  PersonalProunoun(String id) {
    this.id = id;
    try {
      File       file              = new File("src/main/resources/other/personal_pronouns.json");
      JsonNode[] personalProunouns = Config.OBJECT_MAPPER.readValue(file, JsonNode[].class);
      for (JsonNode jsonNode : personalProunouns) {
        if (jsonNode.get("id").asText().equals(id)) {
          this.gender       = Gender.valueOf(jsonNode.get("gender").asText());
          this.possession   = Possession.valueOf(jsonNode.get("possession").asText());
          this.singular     = jsonNode.get("singular").asBoolean();
          this.translations = Config.OBJECT_MAPPER.readValue(jsonNode.get("translations").toString(), new TypeReference<List<Translation>>() {
          });
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static PersonalProunoun getRandomPersonalPronoun() {
    return PersonalProunoun.values()[new Random().nextInt(PersonalProunoun.values().length - 1)];
  }

  public PersonalProunoun getRandomDifferentPersonalPronoun() {
    List<PersonalProunoun> matchingPronouns = Collections.unmodifiableList(Arrays.asList(values()))
                                                         .stream()
                                                         .filter(o -> o != PersonalProunoun.UNDEFINED)
                                                         .filter(o -> o.getPossession() != this.getPossession())
                                                         .collect(Collectors.toList());
    return matchingPronouns.get(new Random().nextInt(matchingPronouns.size()));
  }

  public String getTranslationValue(Lang lang) {
    Optional<Translation> result = translations.stream().filter(o -> o.getLang() == lang).findAny();
    if (result.isEmpty()) {
      System.err.println("No translation found");
      return "";
    }
    return result.get().getValue();
  }


}
