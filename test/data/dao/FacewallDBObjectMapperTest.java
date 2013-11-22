package data.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.neo4j.graphdb.Node;

import static data.dao.MockNodeFactory.createMockNodeWithProperties;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class FacewallDBObjectMapperTest {

    private static class TestDTO {
        public final String aString;
        public final Integer anInteger;
        public final Boolean aBoolean;
        public final Character aCharacter;

        @JsonCreator
        private TestDTO(
                @JsonProperty("aString") String aString,
                @JsonProperty("anInteger") Integer anInteger,
                @JsonProperty("aBoolean") Boolean aBoolean,
                @JsonProperty("aCharacter") Character aCharacter
        ) {
            this.aString = aString;
            this.anInteger = anInteger;
            this.aBoolean = aBoolean;
            this.aCharacter = aCharacter;
        }
    }

    @Test
    public void convert_node_to_dto() {
        Node node = createMockNodeWithProperties(ImmutableMap.<String, Object>of(
                "aString", "test string",
                "anInteger", 80,
                "aBoolean", true,
                "aCharacter", 'f'
        ));

        TestDTO result = FacewallDBObjectMapper.convertValue(node, TestDTO.class);

        assertThat(result.aString, is("test string"));
        assertThat(result.anInteger, is(80));
        assertThat(result.aBoolean, is(true));
        assertThat(result.aCharacter, is('f'));
    }
    
    @Test
    public void convert_node_with_missing_properties_to_dto() {
        Node node = createMockNodeWithProperties(ImmutableMap.<String, Object>of(
                "aString", "test string"
        ));

        TestDTO result = FacewallDBObjectMapper.convertValue(node, TestDTO.class);

        assertThat(result.aString, is("test string"));
        assertThat(result.anInteger, is(nullValue()));
        assertThat(result.aBoolean, is(nullValue()));
        assertThat(result.aCharacter, is(nullValue()));
    }
}
