package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import org.neo4j.graphdb.Node;

import static data.dao.FacewallDBObjectMapper.convertValue;
import static data.dao.database.FacewallDB.NodeIndex.Persons;
import static data.dao.database.FacewallDB.NodeIndex.Teams;
import static data.dao.database.IndexQuery.anIndexLookup;

public class FacewallDAO {
    private final FacewallDB facewallDb;

    public FacewallDAO(FacewallDB facewallDb) {
        this.facewallDb = facewallDb;
    }

    public PersonDTO fetchPerson(PersonId id) {
        Node node = facewallDb.retrieveNodeFromIndex(anIndexLookup()
                .onIndex(Persons)
                .forValue(id.value)
                .build()
        );
        return convertValue(node, JacksonMappedPersonDTO.class);
    }

    public TeamDTO fetchTeam(TeamId id) {
        Node node = facewallDb.retrieveNodeFromIndex(anIndexLookup()
                .onIndex(Teams)
                .forValue(id.value)
                .build()
        );
        return convertValue(node, JacksonMappedTeamDTO.class);
    }
}
