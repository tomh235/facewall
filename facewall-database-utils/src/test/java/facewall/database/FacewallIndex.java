package facewall.database;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;

import java.util.Map;

import static facewall.database.config.FacewallDatabaseConfiguration.IndexConfiguration;

public class FacewallIndex {

    private final Index<Node> nodeIndex;
    private final IndexConfiguration indexConfig;

    public FacewallIndex(IndexManager indexManager, IndexConfiguration indexConfig) {
        this.nodeIndex = indexManager.forNodes(indexConfig.indexName);
        this.indexConfig = indexConfig;
    }

    public void addNode(Node nodeToAdd, Map<String, Object> data) {
        nodeIndex.add(nodeToAdd, indexConfig.key, data.get(indexConfig.key));
    }
}
