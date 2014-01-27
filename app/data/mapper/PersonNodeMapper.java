package data.mapper;

import data.dao.database.RelationshipTypes;
import domain.Person;
import org.neo4j.graphdb.Node;

import java.util.HashMap;
import java.util.Map;

public class PersonNodeMapper {

    public Map<String, String> mapNodeProperties(Person person) {
        Map<String, String> propertyList = new HashMap<>();
        propertyList.put("name", person.name());
        propertyList.put("imageURL", person.picture());
        propertyList.put("email", person.email());

        return propertyList;
    }

    //TODO: Charlie - move this to a relationship mapper
    public Map<Node, RelationshipTypes> mapNodeRelationships(Node teamNode) {
        Map<Node, RelationshipTypes> relationshipList = new HashMap<>();
        relationshipList.put(teamNode, RelationshipTypes.TEAMMEMBER_OF);

        return relationshipList;
    }
}
