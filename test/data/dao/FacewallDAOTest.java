package data.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FacewallDAOTest {

    private static final String teamIndexName = "Teams";
    private static final String personIndexName = "Persons";
    private static final String indexKey = "id";

    @Mock GraphDatabaseService mockDB;
    @Mock IndexManager mockIndexManager;
    @Mock Index<Node> mockTeamIndex;
    @Mock Index<Node> mockPersonIndex;
    private FacewallDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new FacewallDAO(mockDB);

        Transaction mockTransaction = mock(Transaction.class);
        when(mockDB.beginTx()).thenReturn(mockTransaction);
        when(mockDB.index()).thenReturn(mockIndexManager);

        when(mockIndexManager.forNodes(teamIndexName)).thenReturn(mockTeamIndex);
        when(mockIndexManager.forNodes(personIndexName)).thenReturn(mockPersonIndex);
    }

    @Test
    public void fetchPerson_found() {
    }

    private static IndexHits<Node> createMockIndexHit(Node node) {
        IndexHits<Node> mockIndexHit = mock(IndexHits.class);
        when(mockIndexHit.getSingle()).thenReturn(node);
        return mockIndexHit;
    }

    private static Node createMockNode(Map<String, Object> nodeProperties) {
        Node mockNode = mock(Node.class);
        when(mockNode.getPropertyKeys()).thenReturn(nodeProperties.keySet());

        for (String key : nodeProperties.keySet()) {
            when(mockNode.getProperty(key)).thenReturn(nodeProperties.get(key));
        }
        return mockNode;
    }
}
