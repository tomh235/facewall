package data.dao.database;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;

import java.util.ArrayList;
import java.util.List;

public class FacewallDB {
    public enum NodeIndex {

        Persons_Id("Persons_Id", "id"),
        Teams_Id("Teams_Id", "id");

        final String name;

        final String key;
        private NodeIndex(String name, String key) {
            this.name = name;
            this.key = key;
        }

    }
    private final GraphDatabaseService db;

    public FacewallDB(GraphDatabaseService db) {
        this.db = db;
    }

    public Transaction beginTransaction() {
        return db.beginTx();
    }

    public IndexHits<Node> lookupNodesInIndex(IndexQuery indexQuery) {
        Index<Node> index = db.index().forNodes(indexQuery.indexName);
        return index.query(indexQuery.keyName, indexQuery.queriedValue);
    }

    public Node lookupSingleNodeInIndex(IndexQuery indexQuery) {
        return lookupNodesInIndex(indexQuery).getSingle();
    }

    public List<Node> findRelatedNodes(Node node) {
        List<Node> relatedNodes = new ArrayList<>();
        for (Relationship relationship : node.getRelationships()) {
            relatedNodes.add(relationship.getOtherNode(node));
        }

        return relatedNodes;
    }

    //Should throw a checked exception rather than a runtime one
    public Node findSingleRelatedNode(Node node) {
        List<Node> relatedNodes = findRelatedNodes(node);

        if (relatedNodes.size() > 1) {
            throw new RuntimeException("non-singular related nodes!");
        }

        return relatedNodes.isEmpty() ? null : relatedNodes.get(0);
    }
}