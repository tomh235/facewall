package data.factory.team;

import data.mapper.MutableTeam;
import domain.Person;

import java.util.List;

public class DefaultMutableTeam extends MutableTeam {

    private final List<Person> members;

    private DefaultMutableTeam(List<Person> members) {
        this.members = members;
    }

    public static DefaultMutableTeam newMutableTeamWithMembers(List<Person> members) {
        return new DefaultMutableTeam(members);
    }

    @Override public List<Person> members() {
        return members;
    }
}
