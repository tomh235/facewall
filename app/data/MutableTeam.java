package data;

import data.datatype.TeamId;
import data.dto.TeamInformation;
import domain.Person;
import domain.Team;

import java.util.List;

class MutableTeam extends AbstractTeam {

    private List<Person> members;

    public MutableTeam(TeamInformation teamInformation) {
        super(teamInformation);
    }

    final public TeamId getId() {
        return teamInformation.getId();
    }

    @Override public List<Person> members() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }
}
