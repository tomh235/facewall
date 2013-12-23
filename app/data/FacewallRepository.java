package data;

import data.dao.FacewallDAO;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.PersonInformation;
import data.dto.TeamDTO;
import data.dto.TeamInformation;
import domain.Person;
import domain.Query;
import domain.Team;

import java.util.ArrayList;
import java.util.List;

import static data.dto.TeamInformation.noTeamInformation;
import static domain.NoTeam.noTeam;

class FacewallRepository implements Repository {
    private final FacewallDAO dao;

    public FacewallRepository(FacewallDAO dao) {
        this.dao = dao;
    }

    @Override public List<Person> listPersons() {
        return createPersons(dao.fetchPersons());
    }

    @Override public List<Team> listTeams() {
        return createTeams(dao.fetchTeams());
    }

    @Override public List<Person> queryPersons(Query query) {
        return createPersons(dao.queryPersons(query));
    }

    @Override public List<Team> queryTeams(Query query) {
        return createTeams(dao.queryTeams(query));
    }

    @Override
    public Person findPersonById(PersonId id) {
        return createPerson(dao.fetchPerson(id));
    }

    private Team findTeamById(TeamId id) {
        return createTeam(dao.fetchTeam(id));
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

    private List<Team> createTeams(Iterable<TeamDTO> dtos) {
        List<Team> result = new ArrayList<>();
        for (TeamDTO dto : dtos) {
            result.add(createTeam(dto));
        }

        return result;
    }

    private Team createTeam(TeamDTO dto) {
        MutableTeam team = new MutableTeam(dto.teamInformation);

        List<Person> members = new ArrayList<>();
        for (PersonInformation personInformation : dto.memberInformation) {
            MutablePerson person = new MutablePerson(personInformation);
            person.setTeam(team);

            members.add(person);
        }
        team.setMembers(members);

        return team;
    }

    private class LazyTeam extends AbstractTeam {

        private LazyTeam(TeamInformation teamInformation) {
            super(teamInformation);
        }

        @Override
        public List<Person> members() {
            return findTeamById(teamInformation.getId()).members();
        }
    }
}
