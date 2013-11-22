package data.dao.database;

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

import static data.dao.database.FacewallDB.NodeIndex.Persons;
import static data.dao.database.IndexQuery.anIndexLookup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FacewallDBTest {

    @Mock GraphDatabaseService mockDb;
    @Mock IndexManager mockIndexManager;
    @Mock Index<Node> mockIndex;
    @Mock Transaction mockTransaction;
    private FacewallDB facewallDB;

    @Before
    public void setUp() throws Exception {
        facewallDB = new FacewallDB(mockDb);

        mockTransaction = mock(Transaction.class);
        when(mockDb.beginTx()).thenReturn(mockTransaction);
        when(mockDb.index()).thenReturn(mockIndexManager);

        when(mockIndexManager.forNodes(anyString())).thenReturn(mockIndex);
    }

    @Test
    public void node_from_index_lookup() {
        Node node = mock(Node.class);
        IndexHits<Node> mockIndexHit = createMockIndexHit(node);
        when(mockIndex.get(anyString(), any())).thenReturn(mockIndexHit);

        Node result = facewallDB.retrieveNodeFromIndex(anIndexLookup()
                .onIndex(Persons)
                .forValue("1")
                .build()
        );
        assertThat(result, is(sameInstance(node)));
    }

    @Test
    public void node_from_index_lookup_verifyInteractions() {
        IndexHits<Node> mockIndexHit = createMockIndexHit(mock(Node.class));
        when(mockIndex.get(anyString(), any())).thenReturn(mockIndexHit);

        IndexQuery query = anIndexLookup()
                .onIndex(Persons)
                .forValue("expectedValue")
                .build();

        facewallDB.retrieveNodeFromIndex(query);

        verify(mockDb).beginTx();

        verify(mockDb).index();
        verify(mockIndexManager).forNodes(query.indexName);
        verify(mockIndex).get(query.keyName, query.queriedValue);
        verify(mockIndexHit).getSingle();

        verify(mockTransaction).success();
        verify(mockTransaction).finish();
    }

    private static IndexHits<Node> createMockIndexHit(Node node) {
        IndexHits<Node> mockIndexHit = mock(IndexHits.class);
        when(mockIndexHit.getSingle()).thenReturn(node);
        return mockIndexHit;
    }
}
