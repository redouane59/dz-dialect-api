package io.github.Redouane59.dz.model.word;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.complement.Adjective;
import io.github.Redouane59.dz.model.complement.Noun;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.sentence.SentenceSerializer;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize(using = SentenceSerializer.class)
@Setter
@Getter
public class Sentence extends Word {

  private SentenceContent content;

  // used for serialization
  public JsonNode getAdditionalInformations() {
    ObjectNode node = new ObjectMapper().createObjectNode();
    if (content == null) {
      return node;
    }
    if (content.getPronoun() != null) {
      node.put("personal_prounoun", this.content.getPronoun().getTranslationValue(Lang.FR));
    }
    if (this.content.getAbstractVerb() != null) {
      node.put("verb", this.content.getAbstractVerb().getId());
    }
    if (this.content.getAbstractNoun() != null) {
      node.put("noun", this.content.getAbstractNoun().getId());
    }
    if (this.content.getAbstractAdjective() != null) {
      node.put("adjective", this.content.getAbstractAdjective().getId());
    }
    if (this.content.getTense() != null) {
      node.put("tense", this.content.getTense().name());
    }
    if (this.content.getAbstractAdverb() != null) {
      node.put("adverb", this.content.getAbstractAdverb().getId());
    }
/*    if (this.content.getSuffixPronoun() != null) {
      node.put("suffix_pronoun", this.content.getSuffixPronoun().name());
    }*/
    if (this.content.getAbstractQuestion() != null) {
      node.put("question", this.content.getAbstractQuestion().getId());
    }
    if (this.content.getSentenceSchema() != null) {
      node.put("sentence_type", this.content.getSentenceSchema().getId());
    }
    return node;
  }

  @Getter
  @Setter
  @Builder
  public static class SentenceContent {

    private Verb           abstractVerb;
    private PossessiveWord pronoun;
    private AbstractWord   abstractAdverb;
    private AbstractWord   abstractQuestion;
    private Adjective      abstractAdjective;
    private Noun           abstractNoun;
    private Tense          tense;
    private SentenceSchema sentenceSchema;
    private boolean        negation;
    @Builder.Default
    private List<String>   randomFrWords = new ArrayList<>();
  }

}
