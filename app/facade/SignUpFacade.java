package facade;

import data.AdminRepository;
import data.Repository;
import domain.Person;
import domain.Query;
import domain.Team;
import facade.validators.TeamValidator;
import model.UserModel;
import requestmapper.PersonMapper;

import static facade.QueryBuilder.newQuery;

public class SignUpFacade {
    private final AdminRepository adminRepository;
    private final Repository repository;
    private final PersonMapper personMapper;
    private final TeamValidator teamValidator;

    public SignUpFacade(AdminRepository adminRepository,
                        Repository repository, PersonMapper personMapper,
                        TeamValidator teamValidator) {
        this.repository = repository;
        this.adminRepository = adminRepository;
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
        Query teamQuery = newQuery().withKeywords(newUser.team).build();
        return repository.queryTeams(teamQuery).get(0);
    }
}
