package data.dao.database.query;

import data.dao.database.QueryResultRow;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class FacewallQueryResults implements Iterable<QueryResultRow> {

    private List<QueryResultRow> resultRows = new ArrayList<>();

    public void add(Node person, Node team) {
        resultRows.add(new DefaultQueryResultRow(person, team));
    }

    @Override public Iterator<QueryResultRow> iterator() {
        return resultRows.iterator();
    }

    private static class DefaultQueryResultRow implements QueryResultRow {

        private final Node person;
        private final Node team;

        private DefaultQueryResultRow(Node person, Node team) {
            this.person = person;
            this.team = team;
        }

        @Override public Node getPerson() {
            return person;
        }

        @Override public Node getTeam() {
            return team;
        }
    }
}
