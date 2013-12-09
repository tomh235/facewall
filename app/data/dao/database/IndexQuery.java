package data.dao.database;

import static data.dao.database.FacewallDB.*;

public class IndexQuery<T extends NodeIndex> {

    private final T nodeIndex;
    private final Object queriedValue;

    private IndexQuery(Builder<T> builder) {
        this.nodeIndex = builder.nodeIndex;
        this.queriedValue = builder.queriedValue;
    }

    public static Builder<PersonNodeIndex> anIndexLookupForPersons() {
        return new Builder<>();
    }

    public static Builder<TeamNodeIndex> anIndexLookupForTeams() {
        return new Builder<>();
    }

    public String getIndexName() {
        return nodeIndex.getName();
    }

    public String getIndexKey() {
        return nodeIndex.getKey();
    }

    public Object getQueriedValue() {
        return queriedValue;
    }

    public static class Builder<T extends NodeIndex> {
        private T nodeIndex;
        private Object queriedValue = "";

        private Builder() {}

        public IndexQuery<T> build() {
            if(nodeIndex == null) {
                throw new IllegalStateException("must specify what index to query on");
            }

            return new IndexQuery<>(this);
        }

        public Builder<T> onIndex(T nodeIndex) {
            this.nodeIndex = nodeIndex;
            return this;
        }

        public Builder<T> forValue(Object value) {
            this.queriedValue = value;
            return this;
        }

        public Builder<T> forAllValues() {
            this.queriedValue = "*";
            return this;
        }
    }
}
