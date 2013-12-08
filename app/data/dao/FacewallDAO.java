package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.ArrayList;
import java.util.List;

import static data.dao.database.FacewallDB.NodeIndex.Persons_Id;
import static data.dao.database.FacewallDB.NodeIndex.Teams_Id;
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
                .onIndex(Persons_Id)
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
        List<TeamDTO> dtos = new ArrayList<>();

        Transaction tx = db.beginTransaction();
        try {
            IndexQuery query = anIndexLookup()
                .onIndex(Teams_Id)
                .forAllValues()
                .build();

            for (Node teamNode : db.lookupNodesInIndex(query)) {
                List<Node> membersNodes = db.findRelatedNodes(teamNode);

                dtos.add(new TeamDTO(teamNode, membersNodes));
            }
            tx.success();
        } finally {
            tx.finish();
        }

        return dtos;
    }

    public void writePerson() {

    }
}
