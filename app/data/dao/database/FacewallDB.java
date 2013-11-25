package data.dao.database;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

import static data.dao.database.RelationshipTypes.TEAMMEMBER_OF;
import static org.neo4j.graphdb.Direction.OUTGOING;

public class FacewallDB {
    public enum NodeIndex {

        Persons("Persons", "id"),
        Teams("Teams", "id");

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

    public Node retrieveNodeFromIndex(IndexQuery indexQuery) {
        Node result;

        Transaction tx = db.beginTx();
        try {
            result = nodeFromIndex(indexQuery);

            tx.success();
        } finally {
            tx.finish();
        }

        return result;
    }

    //This could have a better name, couldn't think of one
    //Possibly a sign that there is an abstraction here that isn't actually abstracting enough.
    public Node retrieveSingleRelatedNodeForNodeFromIndex(IndexQuery indexQuery) {
        Node result;

        Transaction tx = db.beginTx();
        try {
            Node indexedNode = nodeFromIndex(indexQuery);
            result = indexedNode.getSingleRelationship(TEAMMEMBER_OF, OUTGOING).getEndNode();

            tx.success();
        } finally {
            tx.finish();
        }

        return result;
    }

    private Node nodeFromIndex(IndexQuery query) {
        Node result;Index<Node> index = db.index().forNodes(query.indexName);
        result = index.get(query.keyName, query.queriedValue).getSingle();
        return result;
    }
}
