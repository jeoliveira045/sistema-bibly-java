package org.labs.sistemabiblyjava.entities.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.labs.sistemabiblyjava.entities.Solicitante;

import java.io.IOException;

public class SolicitanteDatabind {
    public static class IdDeserializer extends JsonDeserializer<Solicitante> {
        @Override
        public Solicitante deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            if (node.isNumber()) {
                Solicitante c = new Solicitante();
                c.setId(node.asLong());
                return c;
            } else if (node.isObject()) {
                JsonNode id = node.get("id");
                Solicitante c = new Solicitante();
                c.setId(id.asLong());
                return c;
            }
            return null;
        }
    }

    public static class IdSerializer extends JsonSerializer<Solicitante> {
        @Override
        public void serialize(Solicitante entity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws
                IOException {
            jsonGenerator.writeNumber(entity.getId());
        }
    }
}
