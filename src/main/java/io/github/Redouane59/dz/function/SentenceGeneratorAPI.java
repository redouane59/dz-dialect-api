package io.github.Redouane59.dz.function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.sentence.SentenceGenerator;
import io.github.Redouane59.dz.model.sentence.Sentences;
import io.github.Redouane59.dz.model.verb.Tense;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentenceGeneratorAPI implements HttpFunction {

  private final String countArg              = "count";
  private final String tensesArg             = "tenses";
  private final String verbsArg              = "verbs";
  private final String adjectivesArg         = "adjectives";
  private final String nounsArg              = "nouns";
  private final String schemaArgs            = "schemas";
  private final String affirmationArg        = "affirmation";
  private final String negationArg           = "negation";
  private final String additionalInformation = "additional_information";

  @Override
  public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
    LOGGER.debug("service called");
    httpResponse.appendHeader("Access-Control-Allow-Origin", "*");

    BufferedWriter      writer   = httpResponse.getWriter();
    GeneratorParameters bodyArgs = GeneratorParameters.builder().build();
    try {
      int count = Integer.parseInt(httpRequest.getFirstQueryParameter(countArg).orElse("1"));
      bodyArgs.setCount(count);
      String
          tenses =
          httpRequest.getFirstQueryParameter(tensesArg)
                     .orElse(Tense.PAST + "," + Tense.PAST2 + "," + Tense.PRESENT + "," + Tense.FUTURE + "," + Tense.IMPERATIVE);
      if (!tenses.isEmpty()) {
        bodyArgs.setTenses(Arrays.stream(tenses.split(",", -1)).map(Tense::valueOf).collect(Collectors.toSet()));
      }
      if (httpRequest.getFirstQueryParameter(verbsArg).isPresent()) {
        bodyArgs.setVerbs(Arrays.stream(httpRequest.getFirstQueryParameter(verbsArg).get()
                                                   .split(",", -1)).collect(Collectors.toSet()));
      }
      if (httpRequest.getFirstQueryParameter(nounsArg).isPresent()) {
        bodyArgs.setNouns(Arrays.stream(httpRequest.getFirstQueryParameter(nounsArg).get()
                                                   .split(",", -1)).collect(Collectors.toSet()));
      }
      if (httpRequest.getFirstQueryParameter(adjectivesArg).isPresent()) {
        bodyArgs.setAdjectives(Arrays.stream(httpRequest.getFirstQueryParameter(adjectivesArg).get()
                                                        .split(",", -1)).collect(Collectors.toSet()));
      }
      if (httpRequest.getFirstQueryParameter(schemaArgs).isPresent()) {
        String types = httpRequest.getFirstQueryParameter(schemaArgs).get();
        if (!types.isEmpty()) {
          Set<String> sentenceTypes = Arrays.stream(types.split(",", -1)).collect(Collectors.toSet());
          bodyArgs.setSentenceSchemas(sentenceTypes);
        }
      }
      if (httpRequest.getFirstQueryParameter(affirmationArg).isPresent()) {
        bodyArgs.setPossibleAffirmation(Boolean.parseBoolean(httpRequest.getFirstQueryParameter(affirmationArg).get()));
      }
      if (httpRequest.getFirstQueryParameter(negationArg).isPresent()) {
        bodyArgs.setPossibleNegation(Boolean.parseBoolean(httpRequest.getFirstQueryParameter(negationArg).get()));
      }
      if (httpRequest.getFirstQueryParameter(additionalInformation).isPresent()) {
        Config.SERIALIZE_ADDITIONAL_INFO = Boolean.parseBoolean(httpRequest.getFirstQueryParameter(additionalInformation).get());
      }

      System.out.println(httpRequest.getQueryParameters());
      SentenceGenerator sentenceGenerator = new SentenceGenerator(bodyArgs);
      Sentences         result            = sentenceGenerator.generateRandomSentences();
      httpResponse.setContentType("application/json;charset=UTF-8");
      //httpResponse.appendHeader("content-type", "application/json;charset=UTF-8");
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