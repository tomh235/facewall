package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.dao.database.PersonNodeIndex;
import data.dao.database.TeamNodeIndex;
import data.dao.database.RelationshipTypes;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import domain.Query;
import data.mapper.PersonNodeMapper;
import domain.Person;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.*;

import static data.dao.database.IndexQuery.anIndexLookupForPersons;
import static data.dao.database.IndexQuery.anIndexLookupForTeams;
import static data.dao.database.PersonNodeIndex.Persons_Id;
import static data.dao.database.PersonNodeIndex.Persons_Name;
import static data.dao.database.TeamNodeIndex.Teams_Id;
import static data.dao.database.TeamNodeIndex.Teams_Name;
import static java.util.Collections.emptyList;

public class FacewallDAO {

    private final FacewallDB db;
    private final PersonNodeMapper personNodeMapper;

    public FacewallDAO(FacewallDB facewallDB, PersonNodeMapper personNodeMapper) {
        this.db = facewallDB;
        this.personNodeMapper = personNodeMapper;
    }

    public List<PersonDTO> fetchPersons() {
        List<PersonDTO> dtos = new ArrayList<>();

        IndexQuery<PersonNodeIndex> query = anIndexLookupForPersons()
            .onIndex(Persons_Id)
            .forAllValues()
            .build();

        Transaction tx = db.beginTransaction();
        try {

            dtos = queryIndexForPersons(query);

            tx.success();
        } finally {
            tx.finish();
        }

        return dtos;
    }

    public List<TeamDTO> fetchTeams() {
        List<TeamDTO> dtos = emptyList();

        IndexQuery<TeamNodeIndex> query = anIndexLookupForTeams()
            .onIndex(Teams_Id)
            .forAllValues()
            .build();

        Transaction tx = db.beginTransaction();
        try {
            dtos = queryIndexForTeams(query);

            tx.success();
        } finally {
            tx.finish();
        }
        return dtos;
    }

    public List<PersonDTO> queryPersons(Query expectedQuery) {
        List<PersonDTO> dtos = emptyList();

        IndexQuery<PersonNodeIndex> indexQuery = anIndexLookupForPersons()
            .onIndex(Persons_Name)
            .forValue(expectedQuery.toRegEx())
            .build();

        Transaction tx = db.beginTransaction();
        try {
            dtos = queryIndexForPersons(indexQuery);

            tx.success();
        } finally {
            tx.finish();
        }
        return dtos;
    }

    public List<TeamDTO> queryTeams(Query expectedQuery) {
        List<TeamDTO> dtos = emptyList();

        IndexQuery<TeamNodeIndex> indexQuery = anIndexLookupForTeams()
            .onIndex(Teams_Name)
            .forValue(expectedQuery.toRegEx())
            .build();

        Transaction tx = db.beginTransaction();
        try {
            dtos = queryIndexForTeams(indexQuery);

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

    private List<PersonDTO> queryIndexForPersons(IndexQuery<PersonNodeIndex> indexQuery) {
        List<PersonDTO> result = new ArrayList<>();

        for (Node personNode : db.lookupNodesInIndex(indexQuery)) {
            Node teamNode = db.findSingleRelatedNode(personNode);

            result.add(new PersonDTO(personNode, teamNode));
        }
        return result;
    }

    private List<TeamDTO> queryIndexForTeams(IndexQuery<TeamNodeIndex> query) {
        List<TeamDTO> result = new ArrayList<>();

        for (Node teamNode : db.lookupNodesInIndex(query)) {
            List<Node> membersNodes = db.findRelatedNodes(teamNode);

            result.add(new TeamDTO(teamNode, membersNodes));
        }
        return result;
    }
}
