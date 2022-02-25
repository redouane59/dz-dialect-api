package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.sentence.Sentence;
import java.util.List;
import java.util.Optional;

public class PronounVerbAdjectiveGenerator extends AbstractSentenceGenerator {

  @Override
  public Optional<Sentence> generateSentence() {
    Sentence sentence = new Sentence();

    Optional<VerbConjugation> verbConjugation = this.getRandomVerb();
    if (verbConjugation.isEmpty()) {
      return Optional.empty();
    }

    sentence.setVerbIds(List.of(verbConjugation.get().getVerb().getId()));

    Optional<Adjective> adjective = getRandomAdjective();
    if (adjective.isPresent()) {
      sentence.setAdjectiveIds(List.of(adjective.get().getId()));
      sentence.addDzTranslation(getPronounVerbAdjectiveTranslation(verbConjugation.get(), adjective.get(), Lang.DZ));
      sentence.addFrTranslation(getPronounVerbAdjectiveTranslation(verbConjugation.get(), adjective.get(), Lang.FR));
    } else {
      sentence.addDzTranslation(verbConjugation.get().getConjugation().getDzTranslation());
      sentence.addFrTranslation(verbConjugation.get().getConjugation().getFrTranslation());
    }
    return Optional.of(sentence);
  }

  private String getPronounVerbAdjectiveTranslation(final VerbConjugation verbConjugation, final Adjective adjective, Lang lang) {
    String adjectiveValue = adjective.getTranslationValueByGender(verbConjugation.getConjugation().getGender(),
                                                                  verbConjugation.getConjugation().isSingular(), lang);

    return getProunounVerbString(verbConjugation.getConjugation(), lang) + " " + adjectiveValue;
  }


}
