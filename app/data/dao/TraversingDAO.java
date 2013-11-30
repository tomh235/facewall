package data.dao;

import data.dao.database.FacewallDB;
import data.datatype.PersonId;
import data.datatype.TeamId;
import org.neo4j.graphdb.Node;

import java.util.List;

public class TraversingDAO {

    private final FacewallDB db;

    public TraversingDAO(FacewallDB db) {
        this.db = db;
    }

    public List<Node> fetchTeamMembers(TeamId id) {
        return null;
    }

    public Node fetchTeamForPerson(PersonId personId) {
        return null;
    }
}
