package data.dao;

import data.dao.database.QueryResultRow;
import org.neo4j.graphdb.Node;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

abstract public class MockQueryResultRowFactory {

    private MockQueryResultRowFactory() {}

    public static QueryResultRow createMockQueryResultRow(Node person, Node team) {
        QueryResultRow queryResultRow = mock(QueryResultRow.class);
        when(queryResultRow.getPerson()).thenReturn(person);
        when(queryResultRow.getTeam()).thenReturn(team);

        return queryResultRow;
    }
}
