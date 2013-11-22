package data.factory;

import data.datatype.PersonId;
import data.datatype.TeamId;
import domain.Person;
import domain.Team;

import java.util.List;

class TraversingTeamRepository {

    private final TeamFactory teamFactory;

    TraversingTeamRepository(TeamFactory teamFactory) {
        this.teamFactory = teamFactory;
    }

    public Team findTeamForPerson(PersonId id) {
        return null;
    }
}
