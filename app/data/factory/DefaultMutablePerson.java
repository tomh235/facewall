package data.factory;

import data.mapper.MutablePerson;
import domain.Team;

public class DefaultMutablePerson extends MutablePerson {

    private final Team team;

    private DefaultMutablePerson(Team team) {
        this.team = team;
    }

    public static DefaultMutablePerson newMutablePersonInTeam(Team team) {
        return new DefaultMutablePerson(team);
    }

    @Override public Team team() {
        return team;
    }
}
