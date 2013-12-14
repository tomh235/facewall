package data.dao.database;

import java.util.HashMap;
import java.util.Map;

public class PropertyQuery {

    private static final PropertyQuery allValues = newPropertyQuery().build();

    public final Map<String, String> searchCriteria;

    private PropertyQuery(Builder builder) {
        this.searchCriteria = builder.searchCriteria;
    }

    public static Builder newPropertyQuery() {
        return new Builder();
    }

    public static PropertyQuery newPropertyQueryForAllValues() {
        return allValues;
    }

    public static class Builder {
        private Map<String, String> searchCriteria = new HashMap<>();

        public Builder forProperty(String key, String value) {
            searchCriteria.put(key, value);
            return this;
        }

        public PropertyQuery build() {
            return new PropertyQuery(this);
        }
    }
}
