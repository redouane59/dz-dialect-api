package io.github.Redouane59.dz.function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.sentence.SentenceGenerator;
import io.github.Redouane59.dz.model.sentence.Sentences;
import io.github.Redouane59.dz.model.verb.RootTense;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentenceGeneratorAPI implements HttpFunction {

  @Override
  public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
    LOGGER.debug("service called");
    httpResponse.appendHeader("Access-Control-Allow-Origin", "*");

    BufferedWriter      writer   = httpResponse.getWriter();
    GeneratorParameters bodyArgs = GeneratorParameters.builder().build();
    try {
      Optional<String> count = httpRequest.getFirstQueryParameter("count");
      count.ifPresent(s -> bodyArgs.setCount(Integer.parseInt(s)));
      Optional<String> schema = httpRequest.getFirstQueryParameter("schemas");
      schema.ifPresent(s -> bodyArgs.setSentenceSchemas(Arrays.stream(s.split(",", -1)).collect(Collectors.toSet())));
      Optional<String> tenses = httpRequest.getFirstQueryParameter("tenses");
      tenses.ifPresent(s -> bodyArgs.setTenses(Arrays.stream(s.split(",", -1)).map(RootTense::valueOf).collect(Collectors.toSet())));
      Optional<String> verbs = httpRequest.getFirstQueryParameter("verbs");
      verbs.ifPresent(s -> bodyArgs.setVerbs(Arrays.stream(s.split(",", -1)).collect(Collectors.toSet())));
      Optional<String> nouns = httpRequest.getFirstQueryParameter("nouns");
      nouns.ifPresent(s -> bodyArgs.setNouns(Arrays.stream(s.split(",", -1)).collect(Collectors.toSet())));
      Optional<String> adjectives = httpRequest.getFirstQueryParameter("adjectives");
      adjectives.ifPresent(s -> bodyArgs.setAdjectives(Arrays.stream(s.split(",", -1)).collect(Collectors.toSet())));
      Optional<String> affirmation = httpRequest.getFirstQueryParameter("affirmation");
      affirmation.ifPresent(s -> bodyArgs.setPossibleAffirmation(Boolean.parseBoolean(s)));
      Optional<String> negation = httpRequest.getFirstQueryParameter("negation");
      negation.ifPresent(s -> bodyArgs.setPossibleNegation(Boolean.parseBoolean(s)));
      Optional<String> additionalInformation = httpRequest.getFirstQueryParameter("additional_information");
      additionalInformation.ifPresent(s -> Config.SERIALIZE_ADDITIONAL_INFO = Boolean.parseBoolean(s));
      Optional<String> wordPropositions = httpRequest.getFirstQueryParameter("word_propositions");
      wordPropositions.ifPresent(s -> Config.SERIALIZE_WORD_PROPOSITIONS = Boolean.parseBoolean(s));

      System.out.println(httpRequest.getQueryParameters());
      SentenceGenerator sentenceGenerator = new SentenceGenerator(bodyArgs);
      Sentences         result            = sentenceGenerator.generateRandomSentences();
      httpResponse.setContentType("application/json;charset=UTF-8");
      Config.OBJECT_MAPPER.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
      writer.write(Config.OBJECT_MAPPER.writeValueAsString(result));
    } catch (Exception e) {
      e.printStackTrace();
      writer.write(Config.OBJECT_MAPPER.writeValueAsString(Sentences.builder().errors(Set.of(e.getMessage())).build()));
      httpResponse.setStatusCode(400);
    } finally {
      LOGGER.debug("service finished");
    }
  }

// gcloud functions deploy generate-sentence --entry-point io.github.Redouane59.dz.function.SentenceGeneratorAPI --runtime java11 --trigger-http --memory 128MB --timeout=20 --allow-unauthenticated

}