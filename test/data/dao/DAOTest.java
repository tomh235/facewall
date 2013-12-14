package data.dao;

import data.dao.database.QueryResultRow;
import org.junit.Before;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.IndexHits;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DAOTest {
    protected IndexHits<Node> stubNodeIndexHits;
    protected Transaction mockTransaction;

    @Before
    public void setUpStubs() throws Exception {
        stubNodeIndexHits = mock(IndexHits.class);
        when(stubNodeIndexHits.iterator()).thenReturn(Collections.<Node>emptyList().iterator());

        mockTransaction = mock(Transaction.class);
    }

    protected final QueryResultRow createMockQueryResultRow(Node person, Node team) {
        QueryResultRow queryResultRow = mock(QueryResultRow.class);
        when(queryResultRow.getPerson()).thenReturn(person);
        when(queryResultRow.getTeam()).thenReturn(team);

        return queryResultRow;
    }

    protected final void verifyTransactionComplete() {
        verify(mockTransaction).success();
        verify(mockTransaction).finish();
    }
}
