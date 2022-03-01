package io.github.Redouane59.dz.function;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.generator.NVA.NVASentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVA.PVASentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVN.PVNSentenceBuilder;
import io.github.Redouane59.dz.model.generator.SentenceGenerator;
import io.github.Redouane59.dz.model.sentence.Sentences;
import io.github.Redouane59.dz.model.verb.Tense;
import io.github.Redouane59.dz.model.verb.VerbType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentenceGeneratorAPI implements HttpFunction {

  private final String countArg      = "count";
  private final String tensesArg     = "tenses";
  private final String verbsArg      = "verbs";
  private final String adjectivesArg = "adjectives";
  private final String nounsArg      = "nouns";

  @Override
  public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
    LOGGER.debug("service called");
    BufferedWriter writer   = httpResponse.getWriter();
    BodyArgs       bodyArgs = BodyArgs.builder().build();
    try {
      int count = Integer.parseInt(httpRequest.getFirstQueryParameter(countArg).orElse("1"));
      bodyArgs.setCount(count);
      String tenses = httpRequest.getFirstQueryParameter(tensesArg).orElse(Tense.PAST + "," + Tense.PRESENT + "," + Tense.FUTURE);
      if (!tenses.isEmpty()) {
        bodyArgs.setTenses(Arrays.stream(tenses.split(",", -1)).map(Tense::valueOf)
                                 .collect(Collectors.toList()));
      }
      if (httpRequest.getFirstQueryParameter(verbsArg).isPresent()) {
        bodyArgs.setVerbs(Arrays.stream(httpRequest.getFirstQueryParameter(verbsArg).get()
                                                   .split(",", -1)).collect(Collectors.toList()));
        // no adjectives can be used without state verb
        if (bodyArgs.getVerbsFromIds().stream().filter(o -> o.getVerbType() == VerbType.STATE).findAny().isEmpty()) {
          bodyArgs.setGenerators(List.of(new PVNSentenceBuilder()));
        }
      }
      if (httpRequest.getFirstQueryParameter(nounsArg).isPresent()) {
        bodyArgs.setNouns(Arrays.stream(httpRequest.getFirstQueryParameter(nounsArg).get()
                                                   .split(",", -1)).collect(Collectors.toList()));
        bodyArgs.setGenerators(List.of(new NVASentenceBuilder(), new PVNSentenceBuilder()));
      }
      if (httpRequest.getFirstQueryParameter(adjectivesArg).isPresent()) {
        bodyArgs.setAdjectives(Arrays.stream(httpRequest.getFirstQueryParameter(adjectivesArg).get()
                                                        .split(",", -1)).collect(Collectors.toList()));
        bodyArgs.setGenerators(List.of(new NVASentenceBuilder(), new PVASentenceBuilder()));
      }

      System.out.println(httpRequest.getQueryParameters());
      SentenceGenerator sentenceGenerator = new SentenceGenerator(bodyArgs);
      Sentences         result            = sentenceGenerator.generateRandomSentences();
      httpResponse.setContentType("application/json;charset=iso-8859-1");
      httpResponse.appendHeader("content-type", "application/json;charset=iso-8859-1");
      writer.write(Config.OBJECT_MAPPER.writeValueAsString(result));
    } catch (Exception e) {
      e.printStackTrace();
      //writer.write(Config.OBJECT_MAPPER.writeValueAsString(Sentences.builder().errors(Set.of(e.getMessage())).build()));
      httpResponse.setStatusCode(400);
    } finally {
      LOGGER.debug("service finished");
    }
  }

// gcloud functions deploy generate-sentence --entry-point io.github.Redouane59.dz.function.SentenceGeneratorAPI --runtime java11 --trigger-http --memory 128MB --timeout=20 --allow-unauthenticated

}