package data;

import data.datatype.PersonId;
import data.datatype.TeamId;
import domain.Person;
import domain.Team;

import java.util.List;

public interface Repository {
    public Person findPerson(PersonId personId);
    public Team findTeam(TeamId teamId);
}
