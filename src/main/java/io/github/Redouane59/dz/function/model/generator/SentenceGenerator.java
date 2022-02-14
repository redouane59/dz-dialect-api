package io.github.Redouane59.dz.function.model.generator;

import io.github.Redouane59.dz.function.model.Lang;
import io.github.Redouane59.dz.function.model.Translation;
import io.github.Redouane59.dz.function.model.adjective.AdjectiveRoot;
import io.github.Redouane59.dz.function.model.noun.NounRoot;
import io.github.Redouane59.dz.function.model.noun.WordType;
import io.github.Redouane59.dz.function.model.verb.Conjugation;
import io.github.Redouane59.dz.function.model.verb.Verb;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SentenceGenerator {

  List<Verb>          verbs;
  List<AdjectiveRoot> adjectives;
  List<NounRoot>      nouns;

  public SentenceGenerator(List<Verb> verbs, List<AdjectiveRoot> adjectives, List<NounRoot> nouns) {
    this.verbs      = verbs;
    this.adjectives = adjectives;
    this.nouns      = nouns;
  }

  public List<Translation> generateRandomSentence() {
    //return generateRandomVerbNounSentence();
    return generateRandomVerbAdjectiveSentence();
  }

  public List<Translation> generateRandomVerbNounSentence() {
    // get all the possible verbs
    List<Verb> matchingVerbs = verbs.stream().filter(o -> o.getComplements().contains(WordType.PLACE)).collect(Collectors.toList());
    // pick a random verb
    Verb randomVerb = matchingVerbs.get(new Random().nextInt(matchingVerbs.size()));
    // get all the possible complements nouns
    List<NounRoot> matchingNouns = nouns.stream().filter(o -> randomVerb.getComplements().contains(o.getType())).collect(Collectors.toList());
    // pick a random noun
    NounRoot randomNoun = matchingNouns.get(new Random().nextInt(matchingNouns.size()));
    // pick a random verb conjugation
    Conjugation       randomConjugation = randomVerb.getRandomConjugation();
    List<Translation> result            = new ArrayList<>();
    // build and add dz sentence
    String dzSentence = randomConjugation.getTranslation(Lang.DZ).orElse("*")
                        + " "
                        + randomVerb.getType().getPlacePreposition(Lang.DZ)
                        + " "
                        + randomNoun.getNounValue(true, Lang.DZ).orElse("*");
    result.add(new Translation(Lang.DZ, dzSentence));
    // build and add fr sentence
    String frSentence = randomConjugation.getTranslation(Lang.FR).orElse("*")
                        + " "
                        + randomVerb.getType().getPlacePreposition(Lang.FR)
                        + " "
                        + randomNoun.getGender().getFrArticle()
                        + " "
                        + randomNoun.getNounValue(true, Lang.FR).orElse("*");
    result.add(new Translation(Lang.FR, frSentence));
    return result;
  }

  public List<Translation> generateRandomVerbAdjectiveSentence() {
    // get all the possible verbs
    List<Verb> matchingVerbs = verbs.stream().filter(o -> o.getComplements().contains(WordType.ADJECTIVE)).collect(Collectors.toList());
    // pick a random verb
    Verb randomVerb = matchingVerbs.get(new Random().nextInt(matchingVerbs.size()));
    // pick a random adjective
    AdjectiveRoot randomAdjective = adjectives.get(new Random().nextInt(adjectives.size()));
    // pick a random verb conjugation
    Conjugation       randomConjugation = randomVerb.getRandomConjugation();
    List<Translation> result            = new ArrayList<>();
    // build and add dz sentence
    String dzSentence = randomConjugation.getTranslation(Lang.DZ).orElse("*")
                        + " "
                        + randomAdjective.getAdjective(randomConjugation.getPerson().getGender(), randomConjugation.getPerson().isSingular())
                                         .getTranslation(Lang.DZ).orElse("");

    result.add(new Translation(Lang.DZ, dzSentence));
    // build and add fr sentence
    String frSentence = randomConjugation.getTranslation(Lang.FR).orElse("*")
                        + " "
                        + randomAdjective.getAdjective(randomConjugation.getPerson().getGender(), randomConjugation.getPerson().isSingular())
                                         .getTranslation(Lang.FR).orElse("");

    result.add(new Translation(Lang.FR, frSentence));
    return result;
  }
}
