package data.dao;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.neo4j.graphdb.Node;

import java.io.IOException;

class FacewallDBObjectMapper {
    private static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new SimpleModule()
                    .addSerializer(new NodeSerialiser())
            );

    public static <T> T convertValue(Object object, Class<T> clazz) {
        return objectMapper.convertValue(object, clazz);
    }

    private static class NodeSerialiser extends StdSerializer<Node> {
        private NodeSerialiser() {
            super(Node.class);
        }

        @Override
        public void serialize(Node node, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            for (String key : node.getPropertyKeys()) {
                serializerProvider.defaultSerializeField(key, node.getProperty(key), jsonGenerator);
            }
        }
    }
}
