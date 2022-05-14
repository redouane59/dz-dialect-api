package io.github.Redouane59.dz.helper;

import io.github.Redouane59.dz.model.Articles;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DB {

  public final static Set<Verb>           VERBS                = new HashSet<>();
  public final static Set<Adjective>      ADJECTIVES           = new HashSet<>();
  public final static Set<Noun>           NOUNS                = new HashSet<>();
  public final static Set<AbstractWord>   ADVERBS              = new HashSet<>();
  public final static Articles            ARTICLES             = new Articles("src/main/resources/other/articles.json");
  public final static Set<AbstractWord>   PERSONAL_PRONOUNS_V3 = new HashSet<>();
  public final static Set<SentenceSchema> SENTENCE_SCHEMAS     = new HashSet<>();
  public final static Set<AbstractWord>   QUESTIONS            = new HashSet<>();

  static {
    // verb configurations
    Verb[] verbConfigurations = new Verb[]{};
    try {
      verbConfigurations =
          Config.OBJECT_MAPPER.readValue(new File("./src/main/resources/verbs/.verb_config.json"), Verb[].class);
    } catch (Exception e) {
      System.err.println("could not load verb configurations " + e.getMessage());
      e.printStackTrace();
    }
    // verb translations
    Set<String>
        files =
        new HashSet<>(ResourceList.getResources(Pattern.compile(".*verbs.*json")))
            .stream().filter(o -> !o.contains(".verb_config.json")).collect(Collectors.toSet());
    for (String fileName : files) {
      try {
        Verb           verb              = Config.OBJECT_MAPPER.readValue(new File(fileName), Verb.class);
        Optional<Verb> verbConfiguration = Arrays.stream(verbConfigurations).filter(o -> o.getId().equals(verb.getId())).findFirst();
        if (verbConfiguration.isPresent()) {
          verb.importConfig(verbConfiguration.get());
        } else {
          //System.err.println("no configuration found for verb " + verb.getId());
        }
        VERBS.add(verb);
      } catch (IOException e) {
        System.err.println("could not load verb file " + fileName);
        e.printStackTrace();
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
        ADVERBS.add(Config.OBJECT_MAPPER.readValue(new File(fileName), AbstractWord.class));
      } catch (IOException e) {
        System.err.println("could not load file " + fileName);
      }
    }
    System.out.println(ADVERBS.size() + " adverbs loaded");
  }

  static {
    Set<String> files = new HashSet<>(ResourceList.getResources(Pattern.compile(".*sentences.*json")));
    for (String fileName : files) {
      try {
        SENTENCE_SCHEMAS.add(Config.OBJECT_MAPPER.readValue(new File(fileName), SentenceSchema.class));
      } catch (IOException e) {
        System.err.println("could not load file " + fileName);
      }
    }
    System.out.println(SENTENCE_SCHEMAS.size() + " sentences builders loaded");
  }

  static {
    try {
      QUESTIONS.addAll(List.of(Config.OBJECT_MAPPER.readValue(new File("./src/main/resources/other/questions.json"), AbstractWord[].class)));
    } catch (IOException e) {
      System.err.println("could not load file ");
    }
    System.out.println(QUESTIONS.size() + " questions loaded");
  }

  static {
    try {
      PERSONAL_PRONOUNS_V3.addAll(List.of(Config.OBJECT_MAPPER.readValue(new File("./src/main/resources/other/personal_pronouns.json"),
                                                                         AbstractWord[].class)));
    } catch (IOException e) {
      System.err.println("could not load file ");
    }
    System.out.println(QUESTIONS.size() + " personal pronouns loaded");
  }

}
