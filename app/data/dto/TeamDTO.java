package data.dto;

import org.neo4j.graphdb.Node;

import java.util.List;

public abstract class TeamDTO {
    public final Node teamNode;
    public final List<Node> memberNodes;

    protected TeamDTO(Node teamNode, List<Node> memberNodes) {
        this.teamNode = teamNode;
        this.memberNodes = memberNodes;
    }
}
