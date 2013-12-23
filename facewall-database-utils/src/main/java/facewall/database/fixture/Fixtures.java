package facewall.database.fixture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

public class Fixtures {
    public final Iterable<TeamData> teams;
    public final Iterable<PersonData> teamlessPersons;

    private Fixtures(Builder builder) {
        this.teams = builder.teams;
        this.teamlessPersons = builder.teamlessPersons;
    }

    public static Builder newFixtures() {
        return new Builder();
    }

    public static class Builder {

        private List<TeamData> teams = new ArrayList<>();
        private List<PersonData> teamlessPersons = new ArrayList<>();

        public Builder withTeams(Collection<TeamData.Builder> teamDataBuilders) {
            for (TeamData.Builder builder : teamDataBuilders) {
                teams.add(builder.build());
            }
            return this;
        }

        public Builder withTeams(TeamData.Builder... teamData) {
            return withTeams(asList(teamData));
        }

        public Builder withTeamlessPersons(Collection<PersonData.Builder> personBuilders) {
            for (PersonData.Builder builder : personBuilders) {
                teamlessPersons.add(builder.build());
            }
            return this;
        }

        public Builder withTeamlessPersons(PersonData.Builder... persons) {
            return withTeamlessPersons(asList(persons));
        }

        public Fixtures build() {
            return new Fixtures(this);
        }
    }
}
