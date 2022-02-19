package io.github.Redouane59.dz.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.verb.Verb;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DB {

  public static List<Verb>      VERBS;
  public static List<Adjective> ADJECTIVES;
  public static List<Noun>      NOUNS;

  static {
    try {
      VERBS = Config.OBJECT_MAPPER.readValue(new File("src/main/resources/verbs.json"), new TypeReference<>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static {
    try {
      ADJECTIVES = Config.OBJECT_MAPPER.readValue(new File("src/main/resources/adjectives.json"), new TypeReference<>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static {
    try {
      NOUNS = Config.OBJECT_MAPPER.readValue(new File("src/main/resources/nouns.json"), new TypeReference<>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
