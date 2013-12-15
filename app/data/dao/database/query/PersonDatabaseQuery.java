package data.dao.database.query;

import data.dao.database.QueryResultRow;
import domain.datatype.QueryString;
import org.neo4j.graphdb.Node;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.util.Collections;
import java.util.Map;

import static data.dao.database.FacewallDB.NodeIndex.Persons;

public class PersonDatabaseQuery implements DatabaseQuery {

    private final String id;
    private final Map<String, String> propertyCriteria;

    PersonDatabaseQuery(String id, Map<String, String> propertyCriteria) {
        this.id = id;
        this.propertyCriteria = propertyCriteria;
    }

    @Override public Iterable<QueryResultRow> execute(QueryEngine<Map<String, Object>> queryEngine) {
        String cypherQuery =
            "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ':' + id + "') " +
            "MATCH (person)-[r?]->(team) " +
            "WHERE 1=1 ";

        for (Map.Entry<String, String> entry : propertyCriteria.entrySet()) {
            cypherQuery +=
                "AND person." + entry.getKey() + " =~ '" + entry.getValue() + "' ";
        }

        cypherQuery +=
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
