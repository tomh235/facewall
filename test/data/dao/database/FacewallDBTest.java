package data.dao.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;

import java.util.List;

import static data.dao.database.FacewallDB.NodeIndex.Persons;
import static data.dao.database.IndexQuery.anIndexLookup;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static util.CollectionMatcher.containsExhaustively;

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
    public void beginTransaction_delegates_to_graphDb() {
        when(mockDb.beginTx()).thenReturn(mockTransaction);

        Transaction result = facewallDB.beginTransaction();
        assertThat(result, is(sameInstance(mockTransaction)));
    }

    @Test
    public void beginTransaction_verify_interactions() {
        facewallDB.beginTransaction();
        verify(mockDb).beginTx();
    }

    @Test
    public void nodes_from_index_lookup() {
        IndexHits<Node> expectedHits = mock(IndexHits.class);
        when(mockIndex.query(anyString(), any())).thenReturn(expectedHits);

        IndexHits<Node> result = facewallDB.lookupNodesInIndex(anIndexLookup()
                .onIndex(Persons)
                .forValue("1")
                .build()
        );
        assertThat(result, is(sameInstance(expectedHits)));
    }

    @Test
    public void single_node_from_index_lookup() {
        IndexHits<Node> expectedHits = mock(IndexHits.class);
        when(mockIndex.query(anyString(), any())).thenReturn(expectedHits);

        Node expectedNode = mock(Node.class);
        when(expectedHits.getSingle()).thenReturn(expectedNode);

        Node result = facewallDB.lookupSingleNodeInIndex(anIndexLookup()
                .onIndex(Persons)
                .forValue("1")
                .build()
        );
        assertThat(result, is(sameInstance(expectedNode)));
    }

    @Test
    public void node_from_index_lookup_verifyInteractions() {
        IndexQuery query = anIndexLookup()
                .onIndex(Persons)
                .forValue("expectedValue")
                .build();

        facewallDB.lookupNodesInIndex(query);

        verify(mockDb).index();
        verify(mockIndexManager).forNodes(query.indexName);
        verify(mockIndex).query(query.keyName, query.queriedValue);
    }

    @Test
    public void all_nodes_related_to_given_node() {
        Relationship mockRelationship = mock(Relationship.class);
        Iterable<Relationship> relationships = asList(mockRelationship, mockRelationship);

        Node mockNode = mock(Node.class);
        when(mockNode.getRelationships()).thenReturn(relationships);

        Node expectedNode1 = mock(Node.class);
        Node expectedNode2 = mock(Node.class);

        when(mockRelationship.getOtherNode(any(Node.class)))
                .thenReturn(expectedNode1)
                .thenReturn(expectedNode2);

        List<Node> result = facewallDB.findRelatedNodes(mockNode);

        assertThat(result, containsExhaustively(
            sameInstance(expectedNode1),
            sameInstance(expectedNode2)
        ));
    }

    @Test
    public void all_nodes_related_to_given_node_verifyInteractions() {
        Relationship mockRelationship = mock(Relationship.class);
        Iterable<Relationship> relationships = asList(mockRelationship, mockRelationship);

        Node mockNode = mock(Node.class);
        when(mockNode.getRelationships()).thenReturn(relationships);

        when(mockRelationship.getOtherNode(any(Node.class)))
                .thenReturn(mock(Node.class))
                .thenReturn(mock(Node.class));

        facewallDB.findRelatedNodes(mockNode);

        verify(mockNode).getRelationships();
        verify(mockRelationship, times(2)).getOtherNode(mockNode);
    }

    @Test
    public void single_related_node() {
        Relationship mockRelationship = mock(Relationship.class);
        Iterable<Relationship> relationships = asList(mockRelationship);

        Node mockNode = mock(Node.class);
        when(mockNode.getRelationships()).thenReturn(relationships);

        Node expectedNode = mock(Node.class);

        when(mockRelationship.getOtherNode(any(Node.class)))
            .thenReturn(expectedNode);

        Node result = facewallDB.findSingleRelatedNode(mockNode);

        assertThat(result, is(sameInstance(expectedNode)));
    }

    @Test
    public void single_related_node_verifyInteractions() {
        Relationship mockRelationship = mock(Relationship.class);
        Iterable<Relationship> relationships = asList(mockRelationship);

        Node mockNode = mock(Node.class);
        when(mockNode.getRelationships()).thenReturn(relationships);

        when(mockRelationship.getOtherNode(any(Node.class)))
            .thenReturn(mock(Node.class));

        facewallDB.findRelatedNodes(mockNode);

        verify(mockNode).getRelationships();
        verify(mockRelationship).getOtherNode(mockNode);
    }
}
