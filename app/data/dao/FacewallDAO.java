package data.dao;

import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import org.neo4j.graphdb.Node;

import java.util.List;

public class FacewallDAO {

    public List<PersonDTO> fetchPersons() {
        return null;
    }

    public List<TeamDTO> fetchTeams() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public void writePerson() {

    }

    public List<Node> fetchTeamMembers(TeamId id) {
        return null;
    }

    public Node fetchTeamForPerson(PersonId personId) {
        return null;
    }
}
