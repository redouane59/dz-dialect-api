package io.github.Redouane59.dz.function;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Sentence;
import io.github.Redouane59.dz.model.generator.SentenceGenerator;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentenceGeneratorAPI implements HttpFunction {

  @Override
  public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
    LOGGER.debug("service called");
    BufferedWriter writer     = httpResponse.getWriter();
    String         stringBody = httpRequest.getReader().lines().collect(Collectors.joining());
    BodyArgs       bodyArgs   = BodyArgs.builder().build();
    if (!stringBody.isEmpty()) {
      bodyArgs = Config.OBJECT_MAPPER.readValue(stringBody, BodyArgs.class);
    }
    SentenceGenerator sentenceGenerator = new SentenceGenerator(bodyArgs);
    List<Sentence>    result            = sentenceGenerator.generateRandomSentences();
    httpResponse.setContentType("application/json;charset=UTF-8");
    writer.write(Config.OBJECT_MAPPER.writeValueAsString(result));
    LOGGER.debug("service finished");
  }

// gcloud functions deploy generate-sentence --entry-point io.github.Redouane59.dz.function.SentenceGeneratorAPI --runtime java11 --trigger-http --memory 128MB --timeout=50 --allow-unauthenticated
}