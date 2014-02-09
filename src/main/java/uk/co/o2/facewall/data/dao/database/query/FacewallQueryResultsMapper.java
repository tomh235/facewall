package uk.co.o2.facewall.data.dao.database.query;

import uk.co.o2.facewall.data.dao.database.QueryResultRow;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.data.dto.PersonInformationMapper;
import uk.co.o2.facewall.data.dto.TeamInformation;
import uk.co.o2.facewall.data.dto.TeamInformationMapper;
import org.neo4j.graphdb.Node;

import java.util.Map;

public class FacewallQueryResultsMapper {

    private final PersonInformationMapper personInformationMapper;
    private final TeamInformationMapper teamInformationMapper;

    public FacewallQueryResultsMapper(PersonInformationMapper personInformationMapper, TeamInformationMapper teamInformationMapper) {
        this.personInformationMapper = personInformationMapper;
        this.teamInformationMapper = teamInformationMapper;
    }

    public Iterable<QueryResultRow> map(PersonNodeKey personNodeKey, TeamNodeKey teamNodeKey, Iterable<Map<String, Object>> cypherQueryResults) {
        FacewallQueryResults results = new FacewallQueryResults();

        for (Map<String, Object> cypherQueryResultRow : cypherQueryResults) {
            PersonInformation personInformation = personInformationMapper.map((Node) cypherQueryResultRow.get(personNodeKey.value));
            TeamInformation teamInformation = teamInformationMapper.map((Node) cypherQueryResultRow.get(teamNodeKey.value));

            results.add(personInformation, teamInformation);
        }
        return results;
    }
}
