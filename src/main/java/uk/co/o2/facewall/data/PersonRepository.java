package uk.co.o2.facewall.data;

import uk.co.o2.facewall.data.dao.FacewallDAO;
import uk.co.o2.facewall.data.datatype.PersonId;
import uk.co.o2.facewall.data.dto.PersonDTO;
import uk.co.o2.facewall.data.dto.TeamInformation;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Query;
import uk.co.o2.facewall.domain.Team;

import java.util.ArrayList;
import java.util.List;

import static uk.co.o2.facewall.data.dto.TeamInformation.noTeamInformation;
import static uk.co.o2.facewall.domain.NoTeam.noTeam;

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

        //TODO implement this
        @Override
        public void addMember(Person member) {
            throw new UnsupportedOperationException();
        }
    }
}
