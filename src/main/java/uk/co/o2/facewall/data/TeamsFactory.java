package uk.co.o2.facewall.data;

import uk.co.o2.facewall.data.dao.AdminDAO;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.data.dto.TeamDTO;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Team;

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
