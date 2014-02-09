package uk.co.o2.facewall.data.dto;

import org.junit.Test;
import org.neo4j.graphdb.Node;

import java.util.HashMap;

import static uk.co.o2.facewall.data.dao.MockNodeFactory.createMockNodeWithProperties;
import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static uk.co.o2.facewall.data.dto.PersonInformation.noPersonInformation;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PersonInformationMapperTest {

    private Node mockNode;
    private PersonInformation result;
    private final PersonInformationMapper personInformationMapper = new PersonInformationMapper();

    @Test
    public void map_id() throws Exception {
        mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("id", "some-id");
        }});

        result = personInformationMapper.map(mockNode);
        assertThat(result.getId(), is(newPersonId("some-id")));
    }

    @Test
    public void map_name() throws Exception {
        mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("name", "blinky");
        }});

        result = personInformationMapper.map(mockNode);
        assertThat(result.getName(), is("blinky"));
    }

    @Test
    public void map_picture() throws Exception {
        mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("picture", "blinky.img");
        }});

        result = personInformationMapper.map(mockNode);
        assertThat(result.getPicture(), is("blinky.img"));
    }

    @Test
    public void map_email() throws Exception {
        mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("email", "email@testemail.com");
        }});

        result = personInformationMapper.map(mockNode);
        assertThat(result.getEmail(), is("email@testemail.com"));
    }

    @Test
    public void map_role() throws Exception {
        mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("role", "baker");
        }});

        result = personInformationMapper.map(mockNode);
        assertThat(result.getRole(), is("baker"));
    }

    @Test
    public void map_null_to_no_person_information() throws Exception {
        result = personInformationMapper.map(null);

        assertThat(result, is(sameInstance(noPersonInformation())));
    }
}
