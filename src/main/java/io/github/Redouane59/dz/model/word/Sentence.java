package io.github.Redouane59.dz.model.word;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.sentence.SentenceSchema;
import io.github.Redouane59.dz.model.sentence.SentenceSerializer;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize(using = SentenceSerializer.class)
@Setter
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
    if (this.content.getVerb() != null) {
      node.put("verb", this.content.getVerb().getId());
    }
    if (this.content.getNoun() != null) {
      node.put("noun", this.content.getNoun().getId());
    }
    if (this.content.getAdjective() != null) {
      node.put("adjective", this.content.getAdjective().getId());
    }
    if (this.content.getTense() != null) {
      node.put("tense", this.content.getTense().name());
    }
    if (this.content.getAdverb() != null) {
      node.put("adverb", this.content.getAdverb().getId());
    }
/*    if (this.content.getSuffixPronoun() != null) {
      node.put("suffix_pronoun", this.content.getSuffixPronoun().name());
    }*/
    if (this.content.getQuestion() != null) {
      node.put("question", this.content.getQuestion().getId());
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

    private Verb           verb;
    private PossessiveWord pronoun;
    private AbstractWord   adverb;
    private AbstractWord   question;
    private Adjective      adjective;
    private Noun           noun;
    private Tense          tense;
    private SentenceSchema sentenceSchema;
    private boolean        negation;
  }

}
