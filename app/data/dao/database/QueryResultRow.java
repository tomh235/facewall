package data.dao.database;

import org.neo4j.graphdb.Node;

public interface QueryResultRow {
    Node getPerson();
    Node getTeam();
}
