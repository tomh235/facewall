package data.dao.database.query;

import data.dao.database.FacewallDB;
import data.dao.database.QueryResultRow;
import org.neo4j.graphdb.Node;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.util.Collections;
import java.util.Map;

import static data.dao.database.FacewallDB.NodeIndex.Persons;

public class PersonDatabaseQuery implements DatabaseQuery {

    final String id;

    PersonDatabaseQuery(String id) {
        this.id = id;
    }

    @Override public Iterable<QueryResultRow> execute(QueryEngine<Map<String, Object>> queryEngine) {
        String cypherQuery =
            "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ':' + id + "') " +
            "MATCH (person)-[r?]->(team) " +
            "RETURN person, team";

        QueryResult<Map<String, Object>> cypherResults = queryEngine.query(cypherQuery, Collections.<String, Object>emptyMap());

        FacewallQueryResults facewallQueryResults = new FacewallQueryResults();
        for (Map<String, Object> cypherResult : cypherResults) {
            facewallQueryResults.add(
                (Node) cypherResult.get("person"),
                (Node) cypherResult.get("team")
            );
        }
        return facewallQueryResults;
    }
}
