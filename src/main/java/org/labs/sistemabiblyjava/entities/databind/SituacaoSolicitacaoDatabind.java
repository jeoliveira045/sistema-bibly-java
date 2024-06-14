package org.labs.sistemabiblyjava.entities.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.labs.sistemabiblyjava.entities.SituacaoSolicitacao;

import java.io.IOException;

public class SituacaoSolicitacaoDatabind {
    public static class IdDeserializer extends JsonDeserializer<SituacaoSolicitacao> {
        @Override
        public SituacaoSolicitacao deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            if (node.isNumber()) {
                SituacaoSolicitacao c = new SituacaoSolicitacao();
                c.setId(node.asLong());
                return c;
            } else if (node.isObject()) {
                JsonNode id = node.get("id");
                SituacaoSolicitacao c = new SituacaoSolicitacao();
                c.setId(id.asLong());
                return c;
            }
            return null;
        }
    }

    public static class IdSerializer extends JsonSerializer<SituacaoSolicitacao> {
        @Override
        public void serialize(SituacaoSolicitacao entity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws
                IOException {
            jsonGenerator.writeNumber(entity.getId());
        }
    }
}
