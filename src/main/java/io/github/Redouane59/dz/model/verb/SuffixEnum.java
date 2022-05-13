package io.github.Redouane59.dz.model.verb;

import static io.github.Redouane59.dz.model.sentence.SentenceBuilder.RANDOM;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
// @todo add moi/toi/lui/nous/vous/leur
public enum SuffixEnum {
  I_D, // me
  YOU_S_D, // te
  HE_D, // le
  SHE_D, // la
  WE_D, // nous
  YOU_P_D, // vous
  THEY_D, // les
  I_I, // me
  YOU_S_I, // te
  HE_I, // lui
  SHE_I, // lui
  WE_I, // nous
  YOU_P_I, // vous
  THEY_I; // leur
  // I_IMP_D // -moi
  // I_IMP_I // -moi

  public static final Map<String, String> RULE_MAP = Map.of("ouou", "ouh", "iou", "ih", "aek", "ak",
                                                            "wou", "wah");
  private             Suffix              suffix;

  SuffixEnum() {
    try {
      File       file     = new File("src/main/resources/suffixes/pronoun_suffixes.json");
      JsonNode[] suffixes = Config.OBJECT_MAPPER.readValue(file, JsonNode[].class);
      for (JsonNode jsonNode : suffixes) {
        Suffix s = new Suffix();
        s.setGender(Gender.valueOf(jsonNode.get("gender").asText()));
        s.setPossession(Possession.valueOf(jsonNode.get("possession").asText()));
        s.setSingular(jsonNode.get("singular").asBoolean());
        s.setDirect(jsonNode.get("is_direct").asBoolean());
        s.setTranslations(Config.OBJECT_MAPPER.readValue(jsonNode.get("translations").toString(), new TypeReference<>() {
        }));
        if (jsonNode.get("id").asText().equals(this.name())) {
          this.suffix = s;
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static Suffix getRandomSuffix(final Possession other, boolean isDirect, boolean objectOnly) {
    Gender     randomGender     = Gender.getRandomGender();
    boolean    randomSingular   = RANDOM.nextBoolean();
    Possession randomPossession = Possession.getRandomPosession(other, objectOnly);

    return Arrays.stream(values())
                 .filter(s -> s.getSuffix().isDirect() == isDirect)
                 .filter(s -> s.getSuffix().isSingular() == randomSingular)
                 .filter(s -> s.getSuffix().getPossession() == randomPossession)
                 .filter(s -> s.getSuffix().getGender() == randomGender || s.getSuffix().getGender() == Gender.X || randomGender == Gender.X)
                 .findFirst().get().getSuffix();
  }

  public static Suffix getRandomImperativeSuffix(boolean isDirect, boolean objectOnly) {
    Gender     randomGender   = Gender.getRandomGender();
    boolean    randomSingular = RANDOM.nextBoolean();
    Possession randomPossession;
    if (!objectOnly) {
      List<Possession> matchingPossessions = List.of(Possession.I, Possession.OTHER);
      randomPossession = matchingPossessions.get(RANDOM.nextInt(2));
    } else {
      randomPossession = Possession.OTHER;
    }

    return Arrays.stream(values())
                 .filter(s -> s.getSuffix().isDirect() == isDirect)
                 .filter(s -> s.getSuffix().isSingular() == randomSingular)
                 .filter(s -> s.getSuffix().getPossession() == randomPossession)
                 .filter(s -> s.getSuffix().getGender() == randomGender || s.getSuffix().getGender() == Gender.X || randomGender == Gender.X)
                 .findFirst().get().getSuffix();
  }

  public static Suffix getOppositeSuffix(Suffix suffix) {
    return Arrays.stream(values())
                 .filter(s -> s.getSuffix().isSingular() == suffix.isSingular())
                 .filter(s -> s.getSuffix().getPossession() == suffix.getPossession())
                 .filter(s -> s.getSuffix().getGender() == suffix.getGender())
                 .filter(s -> s.getSuffix().isDirect() != suffix.isDirect)
                 .findFirst().get().getSuffix();
  }

  @Setter
  @Getter
  public static class Suffix extends PossessiveWord {

    @JsonProperty("is_direct")
    private boolean isDirect;
  }

}
