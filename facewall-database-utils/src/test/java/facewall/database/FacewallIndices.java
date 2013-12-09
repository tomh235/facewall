package facewall.database;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexManager;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static facewall.database.config.FacewallDatabaseConfiguration.IndexConfiguration;

public class FacewallIndices {

    private final List<FacewallIndex> indices;

    private FacewallIndices(List<FacewallIndex> indices) {
        this.indices = indices;
    }

    public static FacewallIndices newFacewallIndices(IndexManager indexManager, IndexConfiguration... indexConfigurations) {
        List<FacewallIndex> indices = new ArrayList<>();

        for (IndexConfiguration indexConfig : indexConfigurations) {
            indices.add(new FacewallIndex(indexManager, indexConfig));
        }
        return new FacewallIndices(indices);
    }

    public void addNode(Node nodeToAdd, Map<String, Object> data) {
        for (FacewallIndex index : indices) {
            index.addNode(nodeToAdd, data);
        }
    }
}
