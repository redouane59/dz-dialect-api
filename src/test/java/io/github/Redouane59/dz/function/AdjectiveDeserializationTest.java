package io.github.Redouane59.dz.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import org.junit.jupiter.api.Test;

public class AdjectiveDeserializationTest {

  @Test
  public void adjectiveDeserializationTest() {

    Adjective adjective = DB.ADJECTIVES.get(2);
    assertEquals("petit", adjective.getId());
    assertEquals(4, adjective.getValues().size());
    assertEquals("petit", adjective.getFrValue(Gender.M, true));
    assertEquals("sghir", adjective.getDzValue(Gender.M, true));
    assertEquals("petite", adjective.getFrValue(Gender.F, true));
    assertEquals("sghira", adjective.getDzValue(Gender.F, true));
    assertEquals("petits", adjective.getFrValue(Gender.M, false));
    assertEquals("sghaar", adjective.getDzValue(Gender.M, false));
    assertEquals("petites", adjective.getFrValue(Gender.F, false));
    assertEquals("sghaar", adjective.getDzValue(Gender.F, false));
    assertTrue(adjective.getPossibleNouns().size() > 0);

  }

}
