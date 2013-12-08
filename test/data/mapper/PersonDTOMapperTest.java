package data.mapper;

import data.dao.MockNodeFactory;
import domain.Person;
import domain.Team;
import org.junit.Test;
import org.neo4j.graphdb.Node;

import java.util.HashMap;

import static data.dao.MockNodeFactory.createMockNodeWithProperties;
import static domain.PersonMatcher.aPerson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PersonDTOMapperTest {

    private final PersonDTOMapper personDTOMapper = new PersonDTOMapper();

    private static class TestMutablePerson extends MutablePerson {
        @Override public Team team() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void map_name() {
        Node personNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("name", "Jem Bentham");
        }});

        Person result = personDTOMapper.map(new TestMutablePerson(), personNode);

        assertThat(result, is(aPerson()
            .named("Jem Bentham")
        ));
    }

    @Test
    public void map_picture() {
        Node personNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("picture", "Jem Bentham.img");
        }});

        Person result = personDTOMapper.map(new TestMutablePerson(), personNode);

        assertThat(result, is(aPerson()
            .withPicture("Jem Bentham.img")
        ));
    }
}
