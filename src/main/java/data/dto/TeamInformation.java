package data.dto;

import data.datatype.TeamId;

import static data.datatype.TeamId.newTeamId;
import static data.datatype.TeamId.noTeamId;

public class TeamInformation {

    private static final TeamInformation noTeamInformation = newTeamInformation().build();

    private final TeamId id;
    private final String name;
    private final String colour;

    private TeamInformation(Builder builder) {
        id = builder.id;
        name = builder.name;
        colour = builder.colour;
    }

    public static Builder newTeamInformation() {
        return new Builder();
    }

    public static TeamInformation noTeamInformation() {
        return noTeamInformation;
    }

    public TeamId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamInformation that = (TeamInformation) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static class Builder {
        private TeamId id = noTeamId();
        private String name = "";
        private String colour = "";

        public TeamInformation build() {
            return new TeamInformation(this);
        }

        public Builder withId(String id) {
            this.id = newTeamId(id);
            return this;
        }

        public Builder named(String name) {
            this.name = name;
            return this;
        }

        public Builder coloured(String colour) {
            this.colour = colour;
            return this;
        }
    }
}
