package io.github.Redouane59.dz.helper;

import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.verb.Verb;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class DB {

  public static Set<Verb>      VERBS      = new HashSet<>();
  public static Set<Adjective> ADJECTIVES = new HashSet<>();
  public static Set<Noun>      NOUNS      = new HashSet<>();
  public static Set<Adverb>    ADVERBS    = new HashSet<>();

  static {
    Set<String>
        files =
        new HashSet<>(ResourceList.getResources(Pattern.compile(".*verbs.*json")));
    for (String fileName : files) {
      try {
        VERBS.add(Config.OBJECT_MAPPER.readValue(new File(fileName), Verb.class));
      } catch (IOException e) {
        System.err.println("could not load file " + fileName);
      }
    }
    System.out.println(VERBS.size() + " verbs loaded");
  }

  static {
    Set<String>
        files =
        new HashSet<>(ResourceList.getResources(Pattern.compile(".*adjectives.*json")));
    for (String fileName : files) {
      try {
        ADJECTIVES.add(Config.OBJECT_MAPPER.readValue(new File(fileName), Adjective.class));
      } catch (IOException e) {
        System.err.println("could not load file " + fileName);
      }
    }
    System.out.println(ADJECTIVES.size() + " adjectives loaded");
  }

  static {
    Set<String>
        files =
        new HashSet<>(ResourceList.getResources(Pattern.compile(".*nouns.*json")));
    for (String fileName : files) {
      try {
        NOUNS.add(Config.OBJECT_MAPPER.readValue(new File(fileName), Noun.class));
      } catch (IOException e) {
        System.err.println("could not load file " + fileName);
      }
    }
    System.out.println(NOUNS.size() + " nouns loaded\n");
  }

  static {
    Set<String>
        files =
        new HashSet<>(ResourceList.getResources(Pattern.compile(".*adv.*json")));
    for (String fileName : files) {
      try {
        ADVERBS.add(Config.OBJECT_MAPPER.readValue(new File(fileName), Adverb.class));
      } catch (IOException e) {
        System.err.println("could not load file " + fileName);
      }
    }
    System.out.println(ADVERBS.size() + " verbs loaded");
  }
}
