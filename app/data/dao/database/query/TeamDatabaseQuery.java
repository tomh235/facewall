package data.dao.database.query;

import data.dao.database.QueryResultRow;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.util.Collections;
import java.util.Map;

import static data.dao.database.FacewallDB.NodeIndex.Persons;
import static data.dao.database.FacewallDB.NodeIndex.Teams;
import static data.dao.database.RelationshipTypes.TEAMMEMBER_OF;
import static data.dao.database.query.PersonNodeKey.newPersonNodeKey;
import static data.dao.database.query.TeamNodeKey.newTeamNodeKey;

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

        String cypherQuery =
            "START person = node:" + Persons.getName() + "('" + Persons.getKey() + ":*'), " +
                "team = node:" + Teams.getName() + "('" + Teams.getKey() + ":" + id + "') " +
                "WHERE person-[:" + TEAMMEMBER_OF.name() + "]->team ";

        for (Map.Entry<String, String> entry : propertyCriteria.entrySet()) {
            cypherQuery +=
                "AND team." + entry.getKey() + " =~ '" + entry.getValue() + "' ";
        }

        cypherQuery +=
                "RETURN person, team";

        QueryResult<Map<String,Object>> cypherResults = queryEngine.query(cypherQuery, Collections.<String, Object>emptyMap());
        return queryResultsMapper.map(newPersonNodeKey("person"), newTeamNodeKey("team"), cypherResults);
    }
}
