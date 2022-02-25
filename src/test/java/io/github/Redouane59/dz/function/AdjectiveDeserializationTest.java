package io.github.Redouane59.dz.function;

import static io.github.Redouane59.dz.helper.Config.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AdjectiveDeserializationTest {

  List<Adjective> result = OBJECT_MAPPER.readValue(new File("src/main/resources/adjectives.json"), new TypeReference<>() {
  });

  public AdjectiveDeserializationTest() throws IOException {
  }

  @Test
  public void adjectiveDeserializationTest() {

    Adjective adjective = result.get(0);
    assertEquals("petit", adjective.getId());
    assertEquals(3, adjective.getValues().size());
    assertEquals("petit", adjective.getFrValue(Gender.M, true));
    assertEquals("sghir", adjective.getDzValue(Gender.M, true));
    assertEquals("petite", adjective.getFrValue(Gender.F, true));
    assertEquals("sghira", adjective.getDzValue(Gender.F, true));
    assertEquals("petits", adjective.getFrValue(Gender.X, false));
    assertEquals("sghaar", adjective.getDzValue(Gender.X, false));

  }

}
