package data;

import data.dao.AdminDAO;
import data.dto.PersonInformation;
import data.dto.TeamDTO;
import domain.Person;
import domain.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamsFactory {

    private final AdminDAO adminDAO;

    public TeamsFactory(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public List<Team> createTeams(Iterable<TeamDTO> dtos) {
        List<Team> result = new ArrayList<>();
        for (TeamDTO dto : dtos) {
            result.add(createTeam(dto));
        }

        return result;
    }

    public Team createTeam(TeamDTO dto) {
        MutableTeam team = new MutableTeam(adminDAO, dto.teamInformation);

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
