package io.github.Redouane59.dz.function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.helper.GeneratorParameters;
import io.github.Redouane59.dz.model.WordType;
import io.github.Redouane59.dz.model.sentence.Sentences;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetDatabaseElementsAPI implements HttpFunction {

  private final String wordTypeArg = "word_type";

  @Override
  public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
    LOGGER.debug("service called");
    httpResponse.appendHeader("Access-Control-Allow-Origin", "*");

    BufferedWriter writer = httpResponse.getWriter();
    try {
      httpResponse.setContentType("application/json;charset=UTF-8");
      httpResponse.appendHeader("content-type", "application/json;charset=UTF-8");
      Config.OBJECT_MAPPER.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
      writer.write(Config.OBJECT_MAPPER.writeValueAsString(GeneratorParameters.builder().build()));
    } catch (Exception e) {
      e.printStackTrace();
      String errorMsg = e.getMessage();
      errorMsg +=
          " value should be one of : " + Arrays.stream(WordType.values()).map(WordType::getValue).collect(Collectors.toList());
      writer.write(Config.OBJECT_MAPPER.writeValueAsString(Sentences.builder().errors(Set.of(errorMsg)).build()));
      httpResponse.setStatusCode(400);
    } finally {
      LOGGER.debug("service finished");
    }
  }

// gcloud functions deploy get-elements --entry-point io.github.Redouane59.dz.function.GetDatabaseElementsAPI --runtime java11 --trigger-http --memory 128MB --timeout=20 --allow-unauthenticated

}