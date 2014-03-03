package uk.co.o2.facewall.data.dao.database.query;

import uk.co.o2.facewall.data.dao.database.QueryResultRow;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static uk.co.o2.facewall.data.dao.database.NodeIndex.Persons;
import static uk.co.o2.facewall.data.dao.database.NodeIndex.Teams;
import static uk.co.o2.facewall.data.dao.database.RelationshipTypes.TEAMMEMBER_OF;
import static uk.co.o2.facewall.data.dao.database.query.PersonNodeKey.newPersonNodeKey;
import static uk.co.o2.facewall.data.dao.database.query.TeamNodeKey.newTeamNodeKey;

class TeamDatabaseQuery implements DatabaseQuery {

    private final FacewallQueryResultsMapper queryResultsMapper;
    private final String id;
    private final Map<String, String> propertyCriteria;

    TeamDatabaseQuery(FacewallQueryResultsMapper queryResultsMapper, String id, Map<String, String> propertyCriteria) {
        this.queryResultsMapper = queryResultsMapper;
        this.id = id;
        this.propertyCriteria = propertyCriteria;
    }

    @Override public Iterable<QueryResultRow> execute(QueryEngine<Map<String, Object>> queryEngine) {
        Map<String, Object> parameters = new HashMap<>();
        String cypherQuery =
            "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ":*'), " +
                "team = node:" + Teams.getName() + "('" + Teams.getKey() + ":" + id + "') " +
                "WHERE person-[:" + TEAMMEMBER_OF.name() + "]->team ";

        int i = 0;
        for (Map.Entry<String, String> entry : propertyCriteria.entrySet()) {
            parameters.put("propertyKey" + i, entry.getKey());
            parameters.put("propertyValue" + i, entry.getValue());;
            cypherQuery +=
                "AND team." + "{ propertyKey" + i + " } " + " =~ { propertyValue" + i + " } ";
            i++;
        }

        cypherQuery +=
            "RETURN person, team";

        QueryResult<Map<String,Object>> cypherResults = queryEngine.query(cypherQuery, parameters);
        return queryResultsMapper.map(newPersonNodeKey("person"), newTeamNodeKey("team"), cypherResults);
    }
}
