package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.CustomList;
import io.github.Redouane59.dz.model.Sentence;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.adjective.AdjectiveRoot;
import io.github.Redouane59.dz.model.noun.NounRoot;
import io.github.Redouane59.dz.model.noun.WordType;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.Lang;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SentenceGenerator {

  private BodyArgs bodyArgs = BodyArgs.builder().build();

  public List<Sentence> generateRandomSentences() {
    List<Sentence> sentences = new ArrayList<>();
    for (int i = 0; i < bodyArgs.getCount(); i++) {
      int random = new Random().nextInt(2);
      if (!bodyArgs.getAdjectives().isEmpty() && !bodyArgs.getNouns().isEmpty()) {
        switch (random) {
          case 0:
            sentences.add(generateRandomVerbNounSentence(bodyArgs.getVerbs(), bodyArgs.getTenses(), bodyArgs.getNouns()));
          case 1:
            sentences.add(generateRandomVerbAdjectiveSentence(bodyArgs.getVerbs(), bodyArgs.getTenses(), bodyArgs.getAdjectives()));
        }
      } else if (!bodyArgs.getAdjectives().isEmpty()) {
        sentences.add(generateRandomVerbAdjectiveSentence(bodyArgs.getVerbs(), bodyArgs.getTenses(), bodyArgs.getAdjectives()));
      } else if (!bodyArgs.getNouns().isEmpty()) {
        sentences.add(generateRandomVerbNounSentence(bodyArgs.getVerbs(), bodyArgs.getTenses(), bodyArgs.getNouns()));
      } else {
        Verb randomVerb = getRandomVerb(bodyArgs.getVerbs());
        sentences.add(Sentence.builder()
                              .translations(randomVerb.getRandomConjugation(bodyArgs.getTenses()).getTranslations())
                              .verbs(List.of(randomVerb.getId()))
                              .build());
      }

    }
    return sentences;
  }

  public Sentence generateRandomVerbNounSentence(List<String> verbs, List<Tense> tenses, List<String> nouns) {
    Verb                    randomVerb        = getRandomVerb(verbs);
    Conjugation             randomConjugation = randomVerb.getRandomConjugation(tenses);
    NounRoot                randomNoun        = getRandomNounRoot(nouns, randomVerb);
    CustomList<Translation> result            = new CustomList<>();
    // build and add dz sentence
    String dzVerb       = randomConjugation.getTranslation(Lang.DZ).orElse("*");
    String nounValue    = randomNoun.getNounValue(true, Lang.DZ).orElse("");
    String dzComplement = nounValue;
    String dzSentence   = dzVerb;
    if (!dzComplement.isEmpty()) {
      dzSentence += " ";
      dzSentence += randomVerb.getType().getPlacePreposition(Lang.DZ, nounValue);
      dzSentence += " ";
      dzSentence += dzComplement;
    }
    result.add(new Translation(Lang.DZ, dzSentence));
    // build and add fr sentence
    String frVerb       = randomConjugation.getTranslation(Lang.FR).orElse("*");
    String frComplement = randomNoun.getNounValue(true, Lang.FR).orElse("");
    String frSentence   = frVerb;
    if (!frComplement.isEmpty()) {
      frComplement = randomNoun.getGender().getFrArticle() + " " + frComplement;
      frSentence += " ";
      frSentence += randomVerb.getType().getPlacePreposition(Lang.FR);
      frSentence += " ";
      frSentence += frComplement;
    }
    result.add(new Translation(Lang.FR, frSentence));
    return Sentence.builder()
                   .translations(result)
                   .nounIds(List.of(randomNoun.getId()))
                   .verbs(List.of(randomVerb.getId())).build();
  }

  public Sentence generateRandomVerbAdjectiveSentence(List<String> verbs, List<Tense> tenses, List<String> adjectives) {
    Verb                    randomVerb        = getRandomVerb(verbs);
    AdjectiveRoot           randomAdjective   = getRandomAdjectiveRoot(adjectives);
    Conjugation             randomConjugation = randomVerb.getRandomConjugation(tenses);
    CustomList<Translation> result            = new CustomList<>();
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
    return Sentence.builder()
                   .translations(result)
                   .verbs(List.of(randomVerb.getId()))
                   .adjectiveIds(List.of(randomAdjective.getId()))
                   .build();
  }

  public Verb getRandomVerb(List<String> verbIds) {
    List<Verb> verbs = DB.VERBS.stream().filter(o -> verbIds.contains(o.getId())).collect(Collectors.toList());
    // get all the possible verbs
    List<Verb> matchingVerbs = verbs.stream().filter(o -> o.getComplements().contains(WordType.PLACE)).collect(Collectors.toList());
    // pick a random verb
    return matchingVerbs.get(new Random().nextInt(matchingVerbs.size()));
  }

  public NounRoot getRandomNounRoot(List<String> nounIds, Verb verb) {
    List<NounRoot> nounRoots = DB.NOUNS.stream().filter(o -> nounIds.contains(o.getId())).collect(Collectors.toList());

    if (nounRoots.isEmpty()) {
      return new NounRoot();
    }
    // get all the possible complements nouns
    List<NounRoot> matchingNouns = nounRoots.stream().filter(o -> verb.getComplements().contains(o.getType())).collect(Collectors.toList());
    // pick a random noun
    return matchingNouns.get(new Random().nextInt(matchingNouns.size()));
  }

  public AdjectiveRoot getRandomAdjectiveRoot(List<String> adjectiveIds) {
    List<AdjectiveRoot> adjectives = DB.ADJECTIVES.stream().filter(o -> adjectiveIds.contains(o.getId())).collect(Collectors.toList());

    if (adjectives.isEmpty()) {
      return new AdjectiveRoot();
    }
    return adjectives.get(new Random().nextInt(adjectives.size()));
  }

}
