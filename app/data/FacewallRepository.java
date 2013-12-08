package data;

import data.dao.FacewallDAO;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import data.factory.PersonFactory;
import data.factory.TeamFactory;
import domain.Person;
import domain.Persons;
import domain.Query;
import domain.Team;

import java.util.List;

class FacewallRepository implements Repository {
    final PersonFactory personFactory;
    final TeamFactory teamFactory;

    final FacewallDAO dao;

    public FacewallRepository(PersonFactory personFactory, TeamFactory teamFactory, FacewallDAO dao) {
        this.personFactory = personFactory;
        this.teamFactory = teamFactory;
        this.dao = dao;
    }

    @Override public List<Person> listPersons() {
        List<PersonDTO> dto = dao.fetchPersons();
        return personFactory.createPersons(dto);
    }

    @Override public List<Team> listTeams() {
        List<TeamDTO> dto = dao.fetchTeams();
        return teamFactory.createTeams(dto);
    }

    @Override public List<Person> queryPersons(Query query) {
        return null;
    }

    @Override public List<Team> queryTeams(Query query) {
        return null;
    }

    @Override public void addPerson(Person person) {
        dao.writePerson();
    }
}
