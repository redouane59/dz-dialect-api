package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.Redouane59.dz.model.word.GenderedWord;
import io.github.Redouane59.dz.model.word.PossessiveWord;
import io.github.Redouane59.dz.model.word.Word;
import java.io.IOException;

public class WordFromCSVSerializer extends StdSerializer<Word> {

  public WordFromCSVSerializer() {
    this(null);
  }

  public WordFromCSVSerializer(final Class<Word> t) {
    super(t);
  }

  @Override
  public void serialize(final Word word, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeObjectField("translations", word.getTranslations());
    jsonGenerator.writeObjectField("gender", ((GenderedWord) word).getGender());
    if (((PossessiveWord) word).getPossession() != null) {
      jsonGenerator.writeObjectField("possession", ((PossessiveWord) word).getPossession());
    }
    jsonGenerator.writeObjectField("singular", ((PossessiveWord) word).isSingular());
    jsonGenerator.writeEndObject();
  }
}
