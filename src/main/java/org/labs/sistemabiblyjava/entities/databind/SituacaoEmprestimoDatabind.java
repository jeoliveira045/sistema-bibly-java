package org.labs.sistemabiblyjava.entities.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.labs.sistemabiblyjava.entities.SituacaoEmprestimo;

import java.io.IOException;

public class SituacaoEmprestimoDatabind {
    public static class IdDeserializer extends JsonDeserializer<SituacaoEmprestimo> {
        @Override
        public SituacaoEmprestimo deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            if (node.isNumber()) {
                SituacaoEmprestimo c = new SituacaoEmprestimo();
                c.setId(node.asLong());
                return c;
            } else if (node.isObject()) {
                JsonNode id = node.get("id");
                SituacaoEmprestimo c = new SituacaoEmprestimo();
                c.setId(id.asLong());
                return c;
            }
            return null;
        }
    }

    public static class IdSerializer extends JsonSerializer<SituacaoEmprestimo> {
        @Override
        public void serialize(SituacaoEmprestimo entity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws
                IOException {
            jsonGenerator.writeNumber(entity.getId());
        }
    }
}
