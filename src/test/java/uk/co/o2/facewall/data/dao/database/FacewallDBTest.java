package uk.co.o2.facewall.data.dao.database;

import uk.co.o2.facewall.data.dao.database.query.DatabaseQuery;
import uk.co.o2.facewall.data.dao.database.query.DatabaseQueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;

import static uk.co.o2.facewall.data.dao.database.NodeIndex.Persons;
import static uk.co.o2.facewall.data.dao.database.NodeIndex.Teams;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FacewallDBTest {

    @Mock GraphDatabaseService mockDb;
    @Mock QueryEngine<Map<String,Object>> mockQueryEngine;

    @Mock IndexManager mockIndexManager;
    @Mock Index<Node> mockIndex;
    @Mock Transaction mockTransaction;

    @InjectMocks
    private FacewallDB facewallDB;

    @Before
    public void setUp() throws Exception {
        when(mockDb.beginTx()).thenReturn(mockTransaction);
        when(mockDb.index()).thenReturn(mockIndexManager);

        when(mockIndexManager.forNodes(anyString())).thenReturn(mockIndex);
    }

    @Test
    public void persons_index_name() {
        assertThat(Persons.getName(), is("Persons"));
    }

    @Test
    public void persons_index_key() {
        assertThat(Persons.getKey(), is("id"));
    }

    @Test
    public void teams_index_name() {
        assertThat(Teams.getName(), is("Teams"));
    }

    @Test
    public void teams_index_key() {
        assertThat(Teams.getKey(), is("id"));
    }

    @Test
    public void query_builds_then_executes_query() {
        DatabaseQueryBuilder mockQueryBuilder = mock(DatabaseQueryBuilder.class);
        DatabaseQuery mockQuery = mock(DatabaseQuery.class);
        when(mockQueryBuilder.build()).thenReturn(mockQuery);

        facewallDB.query(mockQueryBuilder);

        verify(mockQueryBuilder).build();
        verify(mockQuery).execute(mockQueryEngine);
    }

    @Test
    public void query_returns_results_of_executing_query() {
        DatabaseQueryBuilder mockQueryBuilder = mock(DatabaseQueryBuilder.class);
        DatabaseQuery mockQuery = mock(DatabaseQuery.class);
        when(mockQueryBuilder.build()).thenReturn(mockQuery);

        Iterable<QueryResultRow> expectedResults = mock(Iterable.class);
        when(mockQuery.execute(any(QueryEngine.class))).thenReturn(expectedResults);

        Iterable<QueryResultRow> results = facewallDB.query(mockQueryBuilder);

        assertThat(results, sameInstance(expectedResults));
    }
}
