package data;

import data.dao.FacewallDAO;
import data.datatype.PersonId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import data.factory.PersonFactory;
import data.factory.TeamFactory;
import data.mapper.PersonNodeMapper;
import domain.Person;
import domain.Persons;
import domain.Query;
import domain.Team;
import org.neo4j.graphdb.Node;

import java.util.List;

class FacewallRepository implements Repository {
    private final PersonFactory personFactory;
    private final TeamFactory teamFactory;
    private final FacewallDAO dao;

    public FacewallRepository(PersonFactory personFactory, TeamFactory teamFactory, FacewallDAO dao) {
        this.personFactory = personFactory;
        this.teamFactory = teamFactory;
        this.dao = dao;
    }

    @Override public List<Person> listPersons() {
        Iterable<PersonDTO> dto = dao.fetchPersons();
        return personFactory.createPersons(dto);
    }

    @Override public List<Team> listTeams() {
        Iterable<TeamDTO> dtos = dao.fetchTeams();
        return teamFactory.createTeams(dtos);
    }

    @Override public List<Person> queryPersons(Query query) {
        Iterable<PersonDTO> dto = dao.queryPersons(query);
        return personFactory.createPersons(dto);
    }

    @Override public List<Team> queryTeams(Query query) {
        Iterable<TeamDTO> dtos = dao.queryTeams(query);
        return teamFactory.createTeams(dtos);
    }

    @Override
    public Person findPersonById(PersonId id) {
        PersonDTO personDTO = dao.fetchPerson(id);
        return personFactory.createPerson(personDTO);
    }
}
