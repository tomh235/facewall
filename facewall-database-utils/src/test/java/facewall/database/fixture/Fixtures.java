package facewall.database.fixture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Fixtures {
    public final Iterable<TeamData> teams;

    private Fixtures(Builder builder) {
        this.teams = builder.teams;
    }

    public Builder newFixtures() {
        return new Builder();
    }

    public class Builder {

        private List<TeamData> teams = new ArrayList<>();

        public Builder withTeams(Collection<TeamData> teamData) {
            this.teams.addAll(teamData);
            return this;
        }

        public Fixtures build() {
            return new Fixtures(this);
        }
    }
}
