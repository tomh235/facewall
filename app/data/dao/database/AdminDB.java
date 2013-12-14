package data.dao.database;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.Map;

public class AdminDB {

    private final GraphDatabaseService graphDatabaseService;

    public AdminDB(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
    }

    public void addPropertiesToNode(Node node, Map<String, String> propertiesList) {
        for (Map.Entry<String, String> property : propertiesList.entrySet()) {
            node.setProperty(property.getKey(), property.getValue());
        }
    }

    public void addRelationshipsToNode(Node startNode, Map<Node,RelationshipTypes> relationshipList) {
        for (Map.Entry<Node,RelationshipTypes> relation : relationshipList.entrySet()) {
            startNode.createRelationshipTo(relation.getKey(), relation.getValue());
        }
    }

    public Node createNode() {
        return graphDatabaseService.createNode();
    }
}
