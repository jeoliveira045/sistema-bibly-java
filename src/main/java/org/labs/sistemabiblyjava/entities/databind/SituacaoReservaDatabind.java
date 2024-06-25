package org.labs.sistemabiblyjava.entities.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.labs.sistemabiblyjava.entities.SituacaoReserva;

import java.io.IOException;

public class SituacaoReservaDatabind {
    public static class IdDeserializer extends JsonDeserializer<SituacaoReserva> {
        @Override
        public SituacaoReserva deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            if (node.isNumber()) {
                SituacaoReserva c = new SituacaoReserva();
                c.setId(node.asLong());
                return c;
            } else if (node.isObject()) {
                JsonNode id = node.get("id");
                SituacaoReserva c = new SituacaoReserva();
                c.setId(id.asLong());
                return c;
            }
            return null;
        }
    }

    public static class IdSerializer extends JsonSerializer<SituacaoReserva> {
        @Override
        public void serialize(SituacaoReserva entity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws
                IOException {
            jsonGenerator.writeNumber(entity.getId());
        }
    }
}
