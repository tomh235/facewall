package facewall.database.fixture;

import facewall.database.util.ForwardingMap;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;

public class PersonData extends ForwardingMap<String, Object> {

    private PersonData(Builder builder) {
        super(new HashMap<>(builder.properties));
    }

    public static Builder newPersonData() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, Object> properties = new HashMap<>();

        public Builder withProperties(Map<String, Object> properties) {
            this.properties.putAll(properties);
            return this;
        }

        public Builder withProperty(String key, String value) {
            this.properties.put(key, value);
            return this;
        }

        public PersonData build() {
            return new PersonData(this);
        }
    }
}
