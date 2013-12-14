package data.dao.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AdminDBTest {

    @Mock GraphDatabaseService mockGraphDatabaseService;

    @InjectMocks
    private AdminDB adminDB;

    @Test
    public void add_properties_to_node_delegates_to_neo4j_add_properties() {
        Node mockNode = mock(Node.class);
        Map<String, String> stubProperties = new HashMap<>();
        stubProperties.put("name", "bob");
        stubProperties.put("imgURL", "http://www.image.jpeg");

        adminDB.addPropertiesToNode(mockNode, stubProperties);

        verify(mockNode).setProperty("name", "bob");
        verify(mockNode).setProperty("imgURL", "http://www.image.jpeg");
    }

    @Test
    public void add_relationships_delegates_to_neo4j_create_relationship_to() {
        Node mockNode = mock(Node.class);
        Node mockTeamNode = mock(Node.class);
        Map<Node, RelationshipTypes> stubProperties = new HashMap<>();
        stubProperties.put(mockTeamNode, RelationshipTypes.TEAMMEMBER_OF);

        adminDB.addRelationshipsToNode(mockNode, stubProperties);

        verify(mockNode).createRelationshipTo(mockTeamNode, RelationshipTypes.TEAMMEMBER_OF);
    }
}
