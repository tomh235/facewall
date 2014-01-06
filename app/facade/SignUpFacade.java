package facade;

import data.PersonRepository;
import data.dao.AdminDAO;
import data.dto.PersonInformation;
import domain.Person;
import domain.Team;

public class SignUpFacade {

    private final AdminDAO adminDAO;
    private final PersonRepository personRepository;

    public SignUpFacade(AdminDAO adminDAO, PersonRepository personRepository) {
        this.adminDAO = adminDAO;
        this.personRepository = personRepository;
    }

    public void registerPerson(PersonInformation personInformation, Team team) {
        adminDAO.savePersonInformation(personInformation);

        Person person = personRepository.findPersonById(personInformation.getId());
        team.addMember(person);
    }
}
