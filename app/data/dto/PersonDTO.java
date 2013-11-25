package data.dto;

import org.neo4j.graphdb.Node;

public class PersonDTO {
    public final Node personNode;
    public final Node teamNode;

    public PersonDTO(Node personNode, Node teamNode) {
        this.personNode = personNode;
        this.teamNode = teamNode;
    }
}
