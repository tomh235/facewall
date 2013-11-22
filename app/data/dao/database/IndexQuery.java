package data.dao.database;

import static data.dao.database.FacewallDB.NodeIndex;

public class IndexQuery {
    public final String indexName;
    public final String keyName;
    public final Object queriedValue;

    private IndexQuery(String indexName, String keyName, Object queriedValue) {
        this.indexName = indexName;
        this.keyName = keyName;
        this.queriedValue = queriedValue;
    }

    public static Builder anIndexLookup() {
        return new Builder();
    }

    public static class Builder {
        private String indexName = "";
        private String indexKeyName = "";
        private Object queriedValue = "";

        private Builder() {}

        public IndexQuery build() {
            return new IndexQuery(indexName, indexKeyName, queriedValue);
        }

        public Builder onIndex(NodeIndex nodeIndex) {
            indexName = nodeIndex.name;
            indexKeyName = nodeIndex.key;
            return this;
        }

        public Builder forValue(Object value) {
            this.queriedValue = value;
            return this;
        }
    }
}
