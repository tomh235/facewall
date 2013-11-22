package data.dao.database;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;

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

    public Node retrieveNodeFromIndex(IndexQuery query) {
        Transaction tx = db.beginTx();

        IndexHits<Node> hits;
        try {
            Index<Node> index = db.index().forNodes(query.indexName);
            hits = index.get(query.keyName, query.queriedValue);

            tx.success();
        } finally {
            tx.finish();
        }

        return hits.getSingle();
    }
}
