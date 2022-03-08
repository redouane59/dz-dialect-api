package io.github.Redouane59.dz.model.generator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.Question;
import io.github.Redouane59.dz.model.adverb.Adverb;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.noun.Noun;
import io.github.Redouane59.dz.model.verb.PersonalProunoun;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.word.Word;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize(using = AbstractSentenceSerializer.class)
public abstract class AbstractSentence extends Word {


  @JsonIgnore
  private PersonalProunoun personalProunoun;
  @JsonIgnore
  private Verb             verb;
  @JsonIgnore
  private Noun             noun;
  @JsonIgnore
  private Adjective        adjective;
  @JsonIgnore
  private Tense            tense;
  @JsonIgnore
  private Adverb           adverb;
  @JsonIgnore
  private PersonalProunoun suffixPronoun;
  @JsonIgnore
  private Question         question;

  @JsonIgnore
  public static String cleanResponse(String result) {
    String newResult = result;
    // replacing je + vowel with j'+vowel
    for (char c : Config.VOWELS) {
      newResult = newResult.replace("je " + c, "j'" + c);
      newResult = newResult.replace("ce " + c, "c'" + c);
      newResult = newResult.replace(" me " + c, " m'" + c);
      newResult = newResult.replace(" te " + c, " t'" + c);
      newResult = newResult.replace(" le " + c, " l'" + c);
      newResult = newResult.replace(" la " + c, " l'" + c);
    }
    newResult = newResult.replace("que il", "qu'il");
    newResult = newResult.replace("que elle", "qu'elle");
    newResult = newResult.replace("que on", "qu'on");
    newResult = newResult.replace("à le", "au");
    newResult = newResult.replace("l' ", "l'");
    return newResult;
  }

  @JsonIgnore
  public abstract String buildSentenceValue(Lang lang);

  public JsonNode getAdditionalInformations() {
    JsonNode node = new ObjectMapper().createObjectNode();
    if (personalProunoun != null) {
      ((ObjectNode) node).put("personal_prounoun", personalProunoun.getId());
    }
    if (verb != null) {
      ((ObjectNode) node).put("verb", verb.getId());
    }
    if (noun != null) {
      ((ObjectNode) node).put("noun", noun.getId());
    }
    if (adjective != null) {
      ((ObjectNode) node).put("adjective", adjective.getId());
    }
    if (tense != null) {
      ((ObjectNode) node).put("tense", tense.getId());
    }
    if (adverb != null) {
      ((ObjectNode) node).put("adverb", adverb.getId());
    }
    if (suffixPronoun != null) {
      ((ObjectNode) node).put("suffix_pronoun", suffixPronoun.getId());
    }
    if (question != null) {
      ((ObjectNode) node).put("question", question.getId());
    }
    return node;
  }

}
