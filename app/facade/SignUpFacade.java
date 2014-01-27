package facade;

import data.PersonRepository;
import data.TeamRepository;
import data.dao.AdminDAO;
import data.dto.PersonInformation;
import domain.Person;
import domain.Team;

import java.util.List;

public class SignUpFacade {

    private final AdminDAO adminDAO;
    private final PersonRepository personRepository;
    private final TeamRepository teamRepository;

    public SignUpFacade(AdminDAO adminDAO, PersonRepository personRepository, TeamRepository teamRepository) {
        this.adminDAO = adminDAO;
        this.personRepository = personRepository;
        this.teamRepository = teamRepository;
    }

    public void registerPerson(PersonInformation personInformation, Team team) {
        adminDAO.savePersonInformation(personInformation);

        Person person = personRepository.findPersonById(personInformation.getId());
        team.addMember(person);
    }

    public List<Team> getAvailableTeams() {
        return teamRepository.listTeams();
    }
}
