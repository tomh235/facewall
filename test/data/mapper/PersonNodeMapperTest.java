package data.mapper;

import data.dao.FacewallDAO;
import data.dao.database.RelationshipTypes;
import domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonNodeMapperTest {
    @Mock private FacewallDAO mockFacewallDAO;

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

        assert(result.containsKey("imgURL"));
        assert(result.get("imgURL").equals("http://www.image.png"));
    }

    @Test
    public void map_node_relationships_maps_team_to_HAS_TEAM_relationship_with_teams_node() {
        Node mockTeamNode = mock(Node.class);
        Map<Node, RelationshipTypes> result = personNodeMapper.mapNodeRelationships(mockTeamNode);

        assert(result.containsKey(mockTeamNode));
        assert(result.get(mockTeamNode).equals(RelationshipTypes.TEAMMEMBER_OF));
    }
}
