package facade;

import data.AdminRepository;
import data.TeamRepository;
import domain.Person;
import domain.Query;
import domain.Team;
import facade.validators.TeamValidator;
import model.UserModel;
import requestmapper.PersonMapper;

import static domain.Query.newQuery;

public class SignUpFacade {
    private final AdminRepository adminRepository;
    private final TeamRepository teamRepository;
    private final PersonMapper personMapper;
    private final TeamValidator teamValidator;

    public SignUpFacade(AdminRepository adminRepository,
                        TeamRepository teamRepository, PersonMapper personMapper,
                        TeamValidator teamValidator) {
        this.adminRepository = adminRepository;
        this.teamRepository = teamRepository;
        this.personMapper = personMapper;
        this.teamValidator = teamValidator;
    }

    public void delegateNewUserToRepository(UserModel userModel) {
        Team userModelsTeam = getUserModelTeamFromRepository(userModel);
        Person person = personMapper.map(userModel, userModelsTeam);
        adminRepository.addPerson(person);
    }

    public boolean validateModelsTeamExists(UserModel newUser) {
        String teamName = newUser.team;
        return teamValidator.validate(teamName);
    }

    protected Team getUserModelTeamFromRepository(UserModel newUser) {
        Query teamQuery = newQuery(newUser.team);
        return teamRepository.queryTeams(teamQuery).get(0);
    }
}
