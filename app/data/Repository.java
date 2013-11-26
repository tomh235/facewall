package data;

import data.datatype.PersonId;
import data.datatype.TeamId;
import domain.Person;
import domain.Team;

import java.util.List;

public interface Repository {
    public List<Person> listPersons();
    public List<Team> listTeams();
    public void addPerson(Person person);
}
