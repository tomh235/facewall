package data.mapper;

import data.dao.database.RelationshipTypes;
import domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.Map;

import static data.dao.database.RelationshipTypes.TEAMMEMBER_OF;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonNodeMapperTest {
    @InjectMocks
    private PersonNodeMapper personNodeMapper;

    @Test
    public void map_node_properties_maps_name_to_name_property() {
        Person mockPerson = mock(Person.class);
        when(mockPerson.name()).thenReturn("bob");

        Map<String, String> result = personNodeMapper.mapNodeProperties(mockPerson);

        assert(result.containsKey("name"));
        assert(result.get("name").equals("bob"));
    }

    @Test
    public void map_node_properties_maps_picture_to_imgURL_property() {
        Person mockPerson = mock(Person.class);
        when(mockPerson.picture()).thenReturn("http://www.image.png");

        Map<String, String> result = personNodeMapper.mapNodeProperties(mockPerson);

        assertThat(result.get("imageURL"), is("http://www.image.png"));
    }

    @Test
    public void map_node_properties_maps_email_to_email_property() {
        Person mockPerson = mock(Person.class);
        when(mockPerson.email()).thenReturn("email@testemail.com");

        Map<String, String> result = personNodeMapper.mapNodeProperties(mockPerson);

        assertThat(result.get("email"), is("email@testemail.com"));
    }

    @Test
    public void map_node_relationships_maps_team_to_HAS_TEAM_relationship_with_teams_node() {
        Node mockTeamNode = mock(Node.class);
        Map<Node, RelationshipTypes> result = personNodeMapper.mapNodeRelationships(mockTeamNode);

        assertThat(result.get(mockTeamNode), is(TEAMMEMBER_OF));
    }
}
