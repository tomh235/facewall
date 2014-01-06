package data.dao;

import data.dao.database.RelationshipTypes;
import data.dto.PersonInformation;
import data.mapper.PersonNodeMapper;
import domain.Person;
import org.neo4j.graphdb.Node;

import java.util.Map;

public class OldAdminDAO {

    private final AdminDAO db;
    private final PersonNodeMapper personNodeMapper;

    public OldAdminDAO(AdminDAO db, PersonNodeMapper personNodeMapper) {
        this.db = db;
        this.personNodeMapper = personNodeMapper;
    }

    public void savePerson(PersonInformation personInformation) {

    }

    public void addPerson(Person person) {
        Node personNode = db.createNode();
        Node teamNode = getTeamByName(person.name());

        Map<String, String> propertiesMap = personNodeMapper.mapNodeProperties(person);
        Map<Node, RelationshipTypes> relationsMap = personNodeMapper.mapNodeRelationships(teamNode);

        db.addPropertiesToNode(personNode, propertiesMap);
        db.addRelationshipsToNode(personNode, relationsMap);
    }

    public Node getTeamByName(String name) {
        return null;
    }
}
