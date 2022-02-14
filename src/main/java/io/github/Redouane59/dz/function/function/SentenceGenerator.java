package io.github.Redouane59.dz.function.function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentenceGenerator implements HttpFunction {


  public SentenceGenerator() {
    File file = new File("src/main/resources/test-twitter-credentials.json");
    if (!file.exists()) {
      LOGGER.error("credentials file not found");
      return;
    }

  }

  @Override
  public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
    LOGGER.debug("service called");
    BufferedWriter writer     = httpResponse.getWriter();
    String         stringBody = httpRequest.getReader().lines().collect(Collectors.joining());
    JsonNode       jsonBody   = NullNode.getInstance();
  /*  if (!stringBody.isEmpty()) {
      jsonBody = TwitterClient.OBJECT_MAPPER.readValue(stringBody, JsonNode.class);
    }
    writer.write(csvResult);
    LOGGER.info(csvResult); */
    LOGGER.debug("service finished");
  }


}