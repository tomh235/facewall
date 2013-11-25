package data.dao.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;

import static data.dao.database.FacewallDB.NodeIndex.Persons;
import static data.dao.database.IndexQuery.anIndexLookup;
import static data.dao.database.RelationshipTypes.TEAMMEMBER_OF;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.neo4j.graphdb.Direction.OUTGOING;

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

    @Test
    public void single_related_node_to_node_from_index_lookup() {
        Node indexedNode = mock(Node.class);

        IndexHits<Node> mockIndexHit = createMockIndexHit(indexedNode);
        when(mockIndex.get(anyString(), any())).thenReturn(mockIndexHit);

        Relationship mockRelationship = mock(Relationship.class);
        when(indexedNode.getSingleRelationship(any(RelationshipType.class), any(Direction.class)))
                .thenReturn(mockRelationship);

        Node relatedNode = mock(Node.class);
        when(mockRelationship.getEndNode()).thenReturn(relatedNode);

        Node result = facewallDB.retrieveSingleRelatedNodeForNodeFromIndex(anIndexLookup()
                .onIndex(Persons)
                .forValue("1")
                .build()
        );
        assertThat(result, is(sameInstance(relatedNode)));
    }

    @Test
    public void single_related_node_to_node_from_index_lookup_verifyInteractions() {
        Node indexedNode = mock(Node.class);

        IndexHits<Node> mockIndexHit = createMockIndexHit(indexedNode);
        when(mockIndex.get(anyString(), any())).thenReturn(mockIndexHit);

        Relationship mockRelationship = mock(Relationship.class);
        when(indexedNode.getSingleRelationship(any(RelationshipType.class), any(Direction.class)))
                .thenReturn(mockRelationship);

        when(mockRelationship.getEndNode()).thenReturn(mock(Node.class));
        IndexQuery query = anIndexLookup()
                .onIndex(Persons)
                .forValue("expectedValue")
                .build();

        facewallDB.retrieveSingleRelatedNodeForNodeFromIndex(query);

        verify(mockDb).beginTx();

        verify(mockDb).index();
        verify(mockIndexManager).forNodes(query.indexName);
        verify(mockIndex).get(query.keyName, query.queriedValue);
        verify(mockIndexHit).getSingle();

        verify(indexedNode).getSingleRelationship(TEAMMEMBER_OF, OUTGOING);
        verify(mockRelationship).getEndNode();

        verify(mockTransaction).success();
        verify(mockTransaction).finish();
    }

    private static IndexHits<Node> createMockIndexHit(Node node) {
        IndexHits<Node> mockIndexHit = mock(IndexHits.class);
        when(mockIndexHit.getSingle()).thenReturn(node);
        return mockIndexHit;
    }
}
