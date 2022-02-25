package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.noun.Noun;
import io.github.Redouane59.dz.model.sentence.Sentence;
import io.github.Redouane59.dz.model.word.AbstractWord;
import java.util.List;
import java.util.Optional;

public class PronounVerbPlaceGenerator extends AbstractSentenceGenerator {

  @Override
  public Optional<Sentence> generateSentence() {
    Sentence sentence = new Sentence();

    Optional<VerbConjugation> verbConjugation = this.getRandomVerb();
    if (verbConjugation.isEmpty()) {
      return Optional.empty();
    }

    sentence.setVerbIds(List.of(verbConjugation.get().getVerb().getId()));

    Optional<Noun> noun = getRandomNoun(verbConjugation.get().getVerb(), WordType.PLACE);
    if (noun.isPresent()) {
      sentence.setNounIds(List.of(noun.get().getId()));
      sentence.addDzTranslation(getPronounVerbNounTranslation(verbConjugation.get(), noun.get(), Lang.DZ));
      sentence.addFrTranslation(getPronounVerbNounTranslation(verbConjugation.get(), noun.get(), Lang.FR));
    } else {
      sentence.addDzTranslation(verbConjugation.get().getConjugation().getDzTranslation());
      sentence.addFrTranslation(verbConjugation.get().getConjugation().getFrTranslation());
    }
    return Optional.of(sentence);
  }

  public String getPronounVerbNounTranslation(VerbConjugation verbConjugation, AbstractWord complement, Lang lang) {
    String noun = complement.getTranslationBySingular(true, lang).get().getValue();

    String result = getProunounVerbString(verbConjugation.getConjugation(), lang);
    result += " ";
    result += verbConjugation.getVerb().getVerbType().getPlacePreposition(lang, noun);
    result += " ";
    result += complement.getWordBySingular(verbConjugation.getConjugation().isSingular(), Lang.FR).getGender().getTranslationValue(lang, noun) + " ";
    result += noun;

    return result;
  }
}
