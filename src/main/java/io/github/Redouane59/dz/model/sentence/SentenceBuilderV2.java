package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.helper.GeneratorParameters;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.complement.Noun;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.AbstractWord;
import io.github.Redouane59.dz.model.word.Conjugation;
import io.github.Redouane59.dz.model.word.Sentence;
import io.github.Redouane59.dz.model.word.Sentence.SentenceContent;
import io.github.Redouane59.dz.model.word.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SentenceBuilderV2 {

  public static Random         RANDOM = new Random();
  private final SentenceSchema schema;
  List<WordTypeAbstractWordTuple> abstractWordListFr;
  List<WordTypeAbstractWordTuple> abstractWordListAr;
  private SentenceContent       sentenceContent;
  private Noun                  nounSubject;
  private Verb                  abstractVerb;
  private Word                  question;
  private Conjugation           suffix;
  private GeneratorParameters   bodyArgs;
  private SentenceBuilderHelper helper;

  public SentenceBuilderV2(SentenceSchema sentenceSchema) {
    this.schema = sentenceSchema;
  }

  public Optional<Sentence> generate(GeneratorParameters bodyArgs) {
    this.bodyArgs   = bodyArgs;
    this.helper     = new SentenceBuilderHelper(bodyArgs, schema);
    sentenceContent = SentenceContent.builder().build();
    boolean resultOk = fillAbstractWordListFromSchema();
    if (!resultOk) {
      System.err.println("no sentence generated");
      return Optional.empty();
    }
    Sentence sentence = new Sentence();
    // sentence.getTranslations().add(generateArTranslation(Lang.DZ));
    // sentence.getTranslations().add(generateFrTranslation());
    sentence.setContent(sentenceContent);
    return Optional.of(sentence);
  }

  private void resetAttributes() {
    abstractVerb       = null;
    nounSubject        = null;
    question           = null;
    abstractWordListFr = new ArrayList<>();
    abstractWordListAr = new ArrayList<>();
    sentenceContent.setSentenceSchema(schema);
    if (schema.isPossibleNegation()) {
      if (bodyArgs.isPossibleNegation() && bodyArgs.isPossibleAffirmation()) {
        sentenceContent.setNegation(RANDOM.nextBoolean());
      } else if (bodyArgs.isPossibleNegation()) {
        sentenceContent.setNegation(true);
      }
    }
  }

  private boolean fillAbstractWordListFromSchema() {
    resetAttributes();
    for (WordType wordType : schema.getFrSequence()) {
      switch (wordType) {
        case NOUN:
          break;
        case VERB:
          break;
        case ADJECTIVE:
          break;
        case PRONOUN:
          break;
        case ADVERB:
          break;
        case QUESTION:
          break;
      }
    }
    return true;
  }

  @AllArgsConstructor
  @Getter
  @Setter
  public static class WordTypeAbstractWordTuple {

    private WordType     wordType;
    private AbstractWord word;
    private boolean      isFirst;
  }

}
