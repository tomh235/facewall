package data.factory;

import data.datatype.TeamId;
import domain.Person;
import domain.Team;

import java.util.List;

abstract class AbstractTeam implements Team {
    final TeamId id;
    final String name;
    final String colour;

    protected AbstractTeam(TeamId id, String name, String colour) {
        this.id = id;
        this.name = name;
        this.colour = colour;
    }

    @Deprecated
    @Override public String id() {
        throw new UnsupportedOperationException("This field is deprecated, and no longer supported");
    }

    @Override public String name() {
        return name;
    }

    @Override public String colour() {
        return colour;
    }

    @Override abstract public List<Person> members();
}
