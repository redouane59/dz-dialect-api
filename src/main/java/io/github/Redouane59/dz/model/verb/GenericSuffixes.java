package io.github.Redouane59.dz.model.verb;

import static io.github.Redouane59.dz.model.sentence.WordPicker.RANDOM;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GenericSuffixes {

  private final Set<Suffix> suffixes = new HashSet<>();

  public GenericSuffixes(String filePath) {
    try {
      File       file           = new File(filePath);
      JsonNode[] objectSuffixes = Config.OBJECT_MAPPER.readValue(file, JsonNode[].class);
      for (JsonNode jsonNode : objectSuffixes) {
        Suffix suffix = Suffix.builder()
                              .gender(Gender.valueOf(jsonNode.get("gender").asText()))
                              .possession(Possession.valueOf(jsonNode.get("possession").asText()))
                              .singular(jsonNode.get("singular").asBoolean())
                              .directValue(jsonNode.get("direct_value").asText())
                              .indirectValue(jsonNode.get("indirect_value").asText()).build();
        suffixes.add(suffix);
      }
    } catch (
        IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public Suffix getSuffix(Gender gender, Possession possession, boolean singular) {
    Optional<Suffix> suffix = suffixes.stream().filter(o -> o.getGender() == gender || o.getGender() == Gender.X || gender == Gender.X)
                                      .filter(o -> o.getPossession() == possession)
                                      .filter(o -> o.isSingular() == singular).findFirst();
    if (suffix.isEmpty()) {
      System.err.println("no suffix found");
      throw new IllegalArgumentException();
    }
    return suffix.get();

  }

  @Getter
  @Builder
  public static class Suffix { // @todo make extends possessiveword

    private Gender     gender;
    private boolean    singular;
    private Possession possession;
    @JsonProperty("direct_value")
    private String     directValue;
    @JsonProperty("indirect_value")
    private String     indirectValue;
    @JsonProperty("direct_value_ar")
    private String     directValueAr;
    @JsonProperty("indirect_value_ar")
    private String     indirectValueAr;

    public static Suffix getRandomSuffix(Possession possession, boolean isObject) {
      Gender     randomGender     = Gender.getRandomGender();
      boolean    randomSingular   = RANDOM.nextBoolean();
      Possession randomPossession = Possession.OTHER;
      if (!isObject) {
        randomPossession = Possession.getRandomPosession(possession);
      }
      return Suffix.builder()
                   .gender(randomGender)
                   .singular(randomSingular)
                   .possession(randomPossession)
                   .build();
    }
  }

}
