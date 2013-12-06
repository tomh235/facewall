package facewall.database.fixture;

import facewall.database.util.ForwardingMap;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class TeamData extends ForwardingMap<String, Object> {

    public final Iterable<PersonData> members;

    private TeamData(Builder builder) {
        super(new HashMap<>(builder.properties));
        this.members = builder.members;
    }

    public final Builder newTeamData() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, Object> properties = emptyMap();
        private Iterable<PersonData> members = emptyList();

        public Builder withProperties(Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }

        public Builder withMembers(PersonData... members) {
            this.members = asList(members);
            return this;
        }

        public TeamData build() {
            return new TeamData(this);
        }
    }
}
