package data.mapper;

import data.datatype.TeamId;
import domain.Person;
import domain.Team;

import java.util.List;

import static data.datatype.TeamId.noTeamId;

public abstract class MutableTeam implements Team {

    protected TeamId id = noTeamId();
    protected String name = "";
    protected String colour = "";

    protected MutableTeam() {}

    final public void setId(TeamId id) {
        this.id = id;
    }

    final @Override public String name() {
        return name;
    }

    final public void setName(String name) {
        this.name = name;
    }

    final @Override public String colour() {
        return colour;
    }

    final public void setColour(String colour) {
        this.colour = colour;
    }

    @Override abstract public List<Person> members();
}
