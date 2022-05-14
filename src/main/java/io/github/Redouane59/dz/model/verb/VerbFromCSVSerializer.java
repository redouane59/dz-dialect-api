package io.github.Redouane59.dz.model.verb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class VerbFromCSVSerializer extends StdSerializer<Verb> {

  public VerbFromCSVSerializer() {
    this(null);
  }

  public VerbFromCSVSerializer(final Class<Verb> t) {
    super(t);
  }

  @Override
  public void serialize(final Verb verb, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("id", verb.getId());
    jsonGenerator.writeObjectField("values", verb.getValues());
    jsonGenerator.writeEndObject();
  }
}
