package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.dao.database.RelationshipTypes;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import data.mapper.PersonNodeMapper;
import domain.Person;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.*;

import static data.dao.database.FacewallDB.NodeIndex.Persons_Id;
import static data.dao.database.FacewallDB.NodeIndex.Teams_Id;
import static data.dao.database.IndexQuery.anIndexLookup;

public class FacewallDAO {

    private final FacewallDB db;
    private final PersonNodeMapper personNodeMapper;

    public FacewallDAO(FacewallDB facewallDB, PersonNodeMapper personNodeMapper) {
        this.db = facewallDB;
        this.personNodeMapper = personNodeMapper;
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

    public void addPerson(Person person) {
        Transaction tx = db.beginTransaction();
        try {
            Node personNode = db.createNode();
            Node teamNode = getTeamByName(person.name());

            Map<String, String> propertiesMap = personNodeMapper.mapNodeProperties(person);
            Map<Node, RelationshipTypes> relationsMap = personNodeMapper.mapNodeRelationships(teamNode);

            db.addPropertiesToNode(personNode, propertiesMap);
            db.addRelationshipsToNode(personNode, relationsMap);

            tx.success();
        } finally {
            tx.finish();
        }
    }

    public Node getTeamByName(String name) {
        return null;
    }
}
