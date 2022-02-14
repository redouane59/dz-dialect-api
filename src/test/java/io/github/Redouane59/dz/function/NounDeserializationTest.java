package io.github.Redouane59.dz.function;

import static io.github.Redouane59.dz.function.helper.Config.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.Redouane59.dz.function.model.Lang;
import io.github.Redouane59.dz.function.model.noun.NounRoot;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class NounDeserializationTest {

  List<NounRoot> result = OBJECT_MAPPER.readValue(new File("src/main/resources/nouns.json"), new TypeReference<>() {
  });

  public NounDeserializationTest() throws IOException {
  }

  @Test
  public void nounDeserializationTest() {

    assertEquals("maison", result.get(0).getFrRoot());
    assertEquals(2, result.get(0).getNouns().size());
    assertTrue(result.get(0).getNouns().get(0).isSingular());
    assertFalse(result.get(0).getNouns().get(1).isSingular());
    assertEquals("maison", result.get(0).getNouns().get(0).getTranslation(Lang.FR));
    assertEquals("dar", result.get(0).getNouns().get(0).getTranslation(Lang.DZ));
  }
}
