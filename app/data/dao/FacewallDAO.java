package data.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import org.neo4j.graphdb.GraphDatabaseService;

public class FacewallDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final GraphDatabaseService db;

    public FacewallDAO(GraphDatabaseService db) {
        this.db = db;
    }

    public PersonDTO fetchPerson(PersonId id) {
        return null;
    }

    public TeamDTO fetchTeam(TeamId id) {
        return null;
    }
}
