package data;

import data.dao.FacewallDAO;
import data.datatype.TeamId;
import data.dto.PersonInformation;
import data.dto.TeamDTO;
import domain.Person;
import domain.Query;
import domain.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamRepository {
    private final FacewallDAO dao;

    public TeamRepository(FacewallDAO dao) {
        this.dao = dao;
    }

    public List<Team> listTeams() {
        return createTeams(dao.fetchTeams());
    }

    public List<Team> queryTeams(Query query) {
        return createTeams(dao.queryTeams(query));
    }

    public Team findTeamById(TeamId id) {
        return createTeam(dao.fetchTeam(id));
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
}
