package data;

import data.dao.FacewallDAO;
import data.datatype.PersonId;
import data.dto.PersonDTO;
import data.dto.TeamInformation;
import domain.Person;
import domain.Query;
import domain.Team;

import java.util.ArrayList;
import java.util.List;

import static data.dto.TeamInformation.noTeamInformation;
import static domain.NoTeam.noTeam;

public class PersonRepository {

    private final TeamRepository teamRepository;
    private final FacewallDAO dao;

    public PersonRepository(TeamRepository teamRepository, FacewallDAO dao) {
        this.teamRepository = teamRepository;
        this.dao = dao;
    }

    public List<Person> listPersons() {
        return createPersons(dao.fetchPersons());
    }

    public List<Person> queryPersons(Query query) {
        return createPersons(dao.queryPersons(query));
    }

    public Person findPersonById(PersonId id) {
        return createPerson(dao.fetchPerson(id));
    }

    private List<Person> createPersons(Iterable<PersonDTO> dtos) {
        List<Person> result = new ArrayList<>();
        for (PersonDTO dto : dtos) {
            result.add(createPerson(dto));
        }

        return result;
    }

    private Person createPerson(PersonDTO dto) {
        MutablePerson person = new MutablePerson(dto.personInformation);

        Team team = dto.teamInformation == noTeamInformation()
                ? noTeam()
                : new LazyTeam(dto.teamInformation);
        person.setTeam(team);

        return person;
    }

    private class LazyTeam extends AbstractTeam {

        private LazyTeam(TeamInformation teamInformation) {
            super(teamInformation);
        }

        @Override
        public List<Person> members() {
            return teamRepository.findTeamById(teamInformation.getId()).members();
        }
    }
}
