package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.ArrayList;
import java.util.List;

import static data.dao.database.FacewallDB.NodeIndex.Persons;
import static data.dao.database.IndexQuery.anIndexLookup;

public class FacewallDAO {

    private final FacewallDB db;

    public FacewallDAO(FacewallDB facewallDB) {
        this.db = facewallDB;
    }

    public List<PersonDTO> fetchPersons() {
        List<PersonDTO> dtos = new ArrayList<>();

        Transaction tx = db.beginTransaction();
        try {
            IndexQuery query = anIndexLookup()
                .onIndex(Persons)
                .forAllValues()
                .build();

            for (Node personNode : db.lookupNodesInIndex(query)) {
                Node teamNode = db.findSingleRelatedNode(personNode);

                dtos.add(new PersonDTO(personNode, teamNode));
            }
            tx.success();
        } finally {
            tx.finish();
        }

        return dtos;
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
