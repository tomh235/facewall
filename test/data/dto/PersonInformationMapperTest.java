package data.dto;

import org.junit.Test;
import org.neo4j.graphdb.Node;

import java.util.HashMap;

import static data.dao.MockNodeFactory.createMockNodeWithProperties;
import static data.datatype.PersonId.newPersonId;
import static data.dto.PersonInformation.noPersonInformation;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PersonInformationMapperTest {

    private final PersonInformationMapper personInformationMapper = new PersonInformationMapper();

    @Test
    public void map_id() throws Exception {
        Node mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("id", "some-id");
        }});
        
        PersonInformation result = personInformationMapper.map(mockNode);
        assertThat(result.getId(), is(newPersonId("some-id")));
    }
    
    @Test
    public void map_name() throws Exception {
        Node mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("name", "blinky");
        }});
        
        PersonInformation result = personInformationMapper.map(mockNode);
        assertThat(result.getName(), is("blinky"));
    }

    @Test
    public void map_picture() throws Exception {
        Node mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("picture", "blinky.img");
        }});

        PersonInformation result = personInformationMapper.map(mockNode);
        assertThat(result.getPicture(), is("blinky.img"));
    }

    @Test
    public void map_email() throws Exception {
        Node mockNode = createMockNodeWithProperties(new HashMap<String, Object>() {{
            put("email", "email@testemail.com");
        }});

        PersonInformation result = personInformationMapper.map(mockNode);
        assertThat(result.getEmail(), is("email@testemail.com"));
    }

    @Test
    public void map_null_to_no_person_information() throws Exception {
        PersonInformation result = personInformationMapper.map(null);

        assertThat(result, is(sameInstance(noPersonInformation())));
    }
}
