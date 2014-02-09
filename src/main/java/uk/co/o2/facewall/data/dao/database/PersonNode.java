package uk.co.o2.facewall.data.dao.database;

import uk.co.o2.facewall.data.dto.PersonInformation;
import org.neo4j.graphdb.Node;

public class PersonNode {

    public final Node wrappedNode;

    private PersonNode(Node wrappedNode) {
        this.wrappedNode = wrappedNode;
    }

    public static PersonNode newPersonNode(Node wrappedNode) {
        return new PersonNode(wrappedNode);
    }

    public void setProperties(PersonInformation personInformation) {
        wrappedNode.setProperty("id", personInformation.getId().value);
        wrappedNode.setProperty("name", personInformation.getName());
        wrappedNode.setProperty("picture", personInformation.getPicture());
        wrappedNode.setProperty("email", personInformation.getEmail());
        wrappedNode.setProperty("role", personInformation.getRole());
    }
}
