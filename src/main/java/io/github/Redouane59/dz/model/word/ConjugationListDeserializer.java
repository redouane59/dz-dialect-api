package io.github.Redouane59.dz.model.word;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Possession;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.verb.Tense;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConjugationListDeserializer extends JsonDeserializer {

  // @todo finish here with X subtypes
  @Override
  public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
  throws IOException {
    JsonNode           arrNode = jsonParser.getCodec().readTree(jsonParser);
    List<? super Word> result  = new ArrayList<>();
    if (arrNode.isArray()) {
      for (final JsonNode node : arrNode) {
        Set<Translation> translations = Config.OBJECT_MAPPER.readValue(node.get("translations").toString(), new TypeReference<Set<Translation>>() {
        });
        if (node.has("singular") && (node.has("gender") || translations.stream().findFirst().get().getGender() != null)) {
          Gender gender = null;
          if (node.has("gender")) {
            gender = Gender.valueOf(node.get("gender").asText());
          }
          boolean singular = node.get("singular").asBoolean();
          if (node.has("possession")) {
            Possession possession = Possession.valueOf(node.get("possession").asText());
            if (node.has("tense")) {
              Tense tense = Tense.valueOf(node.get("tense").asText());
              int   index = -1;
              if (node.has("index")) {
                index = node.get("index").asInt();
              }
              result.add(new Conjugation(translations, gender, singular, possession, tense, index));
            } else {
              result.add(new PossessiveWord(translations, gender, singular, possession));
            }
          } else {
            result.add(new GenderedWord(translations, gender, singular));
          }
        } else {
          result.add(new Word(translations));
        }
      }
    }

    return result;
  }
}
