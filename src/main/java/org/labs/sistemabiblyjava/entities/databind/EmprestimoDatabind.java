package org.labs.sistemabiblyjava.entities.databind;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.labs.sistemabiblyjava.entities.Emprestimo;

import java.io.IOException;

public class EmprestimoDatabind {
    public static class IdDeserializer extends JsonDeserializer<Emprestimo> {
        @Override
        public Emprestimo deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            if (node.isNumber()) {
                Emprestimo c = new Emprestimo();
                c.setId(node.asLong());
                return c;
            } else if (node.isObject()) {
                JsonNode id = node.get("id");
                Emprestimo c = new Emprestimo();
                c.setId(id.asLong());
                return c;
            }
            return null;
        }
    }

    public static class IdSerializer extends JsonSerializer<Emprestimo> {
        @Override
        public void serialize(Emprestimo entity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws
                IOException {
            jsonGenerator.writeNumber(entity.getId());
        }
    }
}
