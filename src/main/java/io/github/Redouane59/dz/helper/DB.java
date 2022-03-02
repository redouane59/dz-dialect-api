package io.github.Redouane59.dz.helper;

import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.verb.Verb;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DB {

  public static List<Verb>      VERBS      = new ArrayList<>();
  public static List<Adjective> ADJECTIVES = new ArrayList<>();
  public static List<Noun>      NOUNS      = new ArrayList<>();

  static {
    List<String>
        files =
        new ArrayList<>(ResourceList.getResources(Pattern.compile(".*verbs.*json")));
    for (String fileName : files) {
      try {
        VERBS.add(Config.OBJECT_MAPPER.readValue(new File(fileName), Verb.class));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println(VERBS.size() + " verbs loaded");
  }

  static {
    List<String>
        files =
        new ArrayList<>(ResourceList.getResources(Pattern.compile(".*adjectives.*json")));
    for (String fileName : files) {
      try {
        ADJECTIVES.add(Config.OBJECT_MAPPER.readValue(new File(fileName), Adjective.class));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println(ADJECTIVES.size() + " adjectives loaded");
  }

  static {
    List<String>
        files =
        new ArrayList<>(ResourceList.getResources(Pattern.compile(".*nouns.*json")));
    for (String fileName : files) {
      try {
        NOUNS.add(Config.OBJECT_MAPPER.readValue(new File(fileName), Noun.class));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println(NOUNS.size() + " nouns loaded\n");

  }
}
