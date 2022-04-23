package io.github.Redouane59.dz.model.sentence;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.Redouane59.dz.helper.Config;
import java.io.IOException;

public class AbstractSentenceSerializer extends StdSerializer<AbstractSentence> {

  public AbstractSentenceSerializer() {
    this(null);
  }

  public AbstractSentenceSerializer(Class<AbstractSentence> t) {
    super(t);
  }

  @Override
  public void serialize(
      AbstractSentence sentence, JsonGenerator jgen, SerializerProvider provider)
  throws IOException {
    jgen.writeStartObject();
    jgen.writeStringField("dz_value", sentence.getDzTranslation());
    jgen.writeStringField("dz_value_ar", sentence.getDzTranslationAr());
    jgen.writeStringField("fr_value", sentence.getFrTranslation());
    if (Config.SERIALIZE_ADDITIONAL_INFO) {
      jgen.writeObjectField("additional_information", sentence.getAdditionalInformations());
    }
    jgen.writeEndObject();
  }

}
