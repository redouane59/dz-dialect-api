package io.github.Redouane59.dz.function;

import static io.github.Redouane59.dz.function.helper.Config.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.Redouane59.dz.function.model.adjective.AdjectiveRoot;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AdjectiveDeserializationTest {

  List<AdjectiveRoot> result = OBJECT_MAPPER.readValue(new File("src/main/resources/adjectives.json"), new TypeReference<>() {
  });

  public AdjectiveDeserializationTest() throws IOException {
  }

  @Test
  public void adjectiveDeserializationTest() {

    assertEquals("petit", result.get(0).getFrRoot());
    assertEquals(3, result.get(0).getAdjectives().size());
    //   assertEquals("petit", result.get(0).getAdjectiveByCriterion(Gender.M, true).getTranslation().getFrValue());
    //   assertEquals("petite", result.get(0).getAdjectiveByCriterion(Gender.F, true).getTranslation().getFrValue());
    //   assertEquals("petits", result.get(0).getAdjectiveByCriterion(Gender.X, false).getTranslation().getFrValue());

  }
}
