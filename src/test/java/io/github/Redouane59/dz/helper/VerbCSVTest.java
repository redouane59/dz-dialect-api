package io.github.Redouane59.dz.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.Redouane59.dz.model.verb.Verb;
import io.github.Redouane59.dz.model.verb.WordFromCSVSerializer;
import io.github.Redouane59.dz.model.word.Word;
import java.nio.file.Paths;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class VerbCSVTest {

  @Test
  public void parseTest() {
    String    fileName = "verb.csv";
    Set<Verb> verbs    = Verb.deserializeFromCSV(fileName, true);

    verbs.forEach(o -> {
      ObjectMapper mapper = new ObjectMapper();
      SimpleModule module = new SimpleModule();
      module.addSerializer(Word.class, new WordFromCSVSerializer());
      mapper.registerModule(module);
      try {
        System.out.println(Verb.OBJECT_MAPPER.writeValueAsString(o));
        mapper.writeValue(Paths.get("./src/test/resources/imported_verbs/" + o.getId() + ".json").toFile(), o);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

}
