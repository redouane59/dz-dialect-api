package io.github.Redouane59.dz.model.word;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.Redouane59.dz.model.sentence.SentenceSerializer;

@JsonSerialize(using = SentenceSerializer.class)
public class Sentence extends Word {

  // used for serialization
  public JsonNode getAdditionalInformations() {
    ObjectNode node = new ObjectMapper().createObjectNode();
    /*if (this.getContent().getPersonalProunoun() != null) {
      node.put("personal_prounoun", this.getContent().getPersonalProunoun().getTranslationValue(Lang.FR));
    }
    if (this.getContent().getVerb() != null) {
      node.put("verb", this.getContent().getVerb().getId());
    }
    if (this.getContent().getNoun() != null) {
      node.put("noun", this.getContent().getNoun().getId());
    }
    if (this.getContent().getAdjective() != null) {
      node.put("adjective", this.getContent().getAdjective().getId());
    }
    if (this.getContent().getTense() != null) {
      node.put("tense", this.getContent().getTense().getId());
    }
    if (this.getContent().getAdverb() != null) {
      node.put("adverb", this.getContent().getAdverb().getId());
    }
    if (this.getContent().getSuffixPronoun() != null) {
      node.put("suffix_pronoun", this.getContent().getSuffixPronoun().name());
    }
    if (this.getContent().getQuestion() != null) {
      node.put("question", this.getContent().getQuestion().name());
    }*/
    return node;
  }

}
