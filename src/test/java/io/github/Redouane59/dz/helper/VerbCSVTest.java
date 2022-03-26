package io.github.Redouane59.dz.helper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.Verb;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VerbCSVTest {

  @Test
  public void parseTest() {
    String             fileName = "verb.csv";
    List<List<String>> entries  = FileHelper.getCsv(getClass().getClassLoader().getResource(fileName).getPath(), ";");
    assertNotNull(entries);
    int               verbInfinitiveIndex = 0;
    int               tenseIndex          = 0;
    int               possessionIndex     = 0;
    int               genderIndex         = 0;
    int               singularIndex       = 0;
    int               langIndex           = 0;
    int               valueIndex          = 0;
    Verb              verb                = new Verb();
    List<Conjugator>  conjugators         = new ArrayList<>();
    List<Conjugation> conjugations        = new ArrayList<>();
    for (List<String> values : entries) {

    }
  }

}
