package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.function.BodyArgs;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.sentence.Sentence;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SentenceGenerator {

  private BodyArgs bodyArgs = BodyArgs.builder().build();

  public List<Sentence> generateRandomSentences() {
    List<Sentence> sentences = new ArrayList<>();
    for (int i = 0; i < bodyArgs.getCount(); i++) {
      sentences.add(generateRandomSentence(bodyArgs.getTenses()));
    }
    return sentences;
  }

  public Sentence generateRandomSentence(List<Tense> tenses) {

    Sentence sentence = new Sentence();
    // verb
    VerbConjugation verbConjugation = getRandomVerb(tenses);
    sentence.setVerbIds(List.of(verbConjugation.getVerb().getId()));
    // complement
    Optional<AbstractWord> complement = getRandomComplement(verbConjugation.getVerb());
    if (complement.isPresent()) {
      if (complement.get() instanceof Noun) {
        sentence.setNounIds(List.of(complement.get().getId()));
        sentence.addDzTranslation(getNounTranslation(verbConjugation, complement.get(), Lang.DZ));
        sentence.addFrTranslation(getNounTranslation(verbConjugation, complement.get(), Lang.FR));
      } else if (complement.get() instanceof Adjective) {
        sentence.setAdjectiveIds(List.of(complement.get().getId()));
        sentence.addDzTranslation(getAdjectiveTranslation(verbConjugation.getConjugation(), complement.get(), Lang.DZ));
        sentence.addFrTranslation(getAdjectiveTranslation(verbConjugation.getConjugation(), complement.get(), Lang.FR));
      }

    } else {
      sentence.addDzTranslation(verbConjugation.getConjugation().getDzTranslation());
      sentence.addFrTranslation(verbConjugation.getConjugation().getFrTranslation());
    }

    return sentence;

  }

  public String getNounTranslation(VerbConjugation verbConjugation, AbstractWord complement, Lang lang) {
    String complementValue = complement.getTranslationBySingular(true, lang).getValue();
    String result          = verbConjugation.getConjugation().getTranslationValue(lang);
    result += " ";
    result += verbConjugation.getVerb().getVerbType().getPlacePreposition(lang);
    result += " ";
    if (lang == Lang.FR) {
      result += complement.getWordBySingular(verbConjugation.getConjugation().isSingular(), Lang.FR).getGender().getFrArticle() + " ";
    }
    result += complementValue;

    return result;
  }

  public String getAdjectiveTranslation(PossessiveWord conjugation, AbstractWord complement, Lang lang) {
    String complementValue = complement.getTranslationByGender(conjugation.getGender(), conjugation.isSingular(), lang).getValue();
    return conjugation.getTranslationValue(lang) + " " + complementValue;
  }

  public VerbConjugation getRandomVerb(List<Tense> tenses) {
    // get all the possible verbs
    List<Verb> matchingVerbs = DB.VERBS.stream()
                                       //  .filter(complement::contains)
                                       .filter(o -> o.getRandomConjugation(tenses).isPresent()).collect(Collectors.toList());
    // pick a random verb
    if (matchingVerbs.isEmpty()) {
      System.err.println("No verb found with matching given verbs & tenses");
      return new VerbConjugation();
    }
    Verb           randomVerb        = matchingVerbs.get(new Random().nextInt(matchingVerbs.size()));
    PossessiveWord randomConjugation = randomVerb.getRandomConjugation(tenses).get();
    return new VerbConjugation(randomVerb, randomConjugation);
  }


  public Optional<AbstractWord> getRandomComplement(Verb verb) {
    List<? extends AbstractWord> allComplements = Stream.concat(DB.NOUNS.stream(), DB.ADJECTIVES.stream())
                                                        .collect(Collectors.toList());
    // get all the possible complements
    List<? extends AbstractWord> matchingComplements = allComplements.stream()
                                                                     .filter(o -> verb.getPossibleComplements().contains(o.getWordType()))
                                                                     .collect(Collectors.toList());
    if (matchingComplements.isEmpty()) {
      System.err.println("no complement found");
      return Optional.empty();
    }

    // pick a random root
    return Optional.of(matchingComplements.get(new Random().nextInt(matchingComplements.size())));
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  public static class VerbConjugation {

    private Verb           verb;
    private PossessiveWord conjugation;
  }

}
