package facewall.database.fixture;

import facewall.database.util.ForwardingMap;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class TeamData extends ForwardingMap<String, Object> {

    public final Iterable<PersonData> members;

    private TeamData(Builder builder) {
        super(new HashMap<>(builder.properties));
        this.members = builder.members;
    }

    public static Builder newTeamData() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, Object> properties = new HashMap<>();
        private List<PersonData> members = new ArrayList<>();

        public Builder withProperties(Map<String, Object> properties) {
            this.properties.putAll(properties);
            return this;
        }

        public Builder withProperty(String key, Object value) {
            this.properties.put(key, value);
            return this;
        }

        public Builder withMembers(PersonData.Builder... members) {
            return withMembers(asList(members));
        }

        public Builder withMembers(Collection<PersonData.Builder> membersBuilders) {
            for (PersonData.Builder builder : membersBuilders) {
                members.add(builder.build());
            }
            return this;
        }

        public TeamData build() {
            return new TeamData(this);
        }
    }
}
