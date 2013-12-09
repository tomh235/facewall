package facade;

import data.Repository;
import domain.Person;
import domain.Query;
import domain.Team;
import facade.validators.TeamValidator;
import model.UserModel;
import requestmapper.PersonMapper;
import requestmapper.QueryMapper;

public class SignUpFacade {
    private final Repository repository;
    private final PersonMapper personMapper;
    private final QueryMapper queryMapper;
    private final TeamValidator teamValidator;

    public SignUpFacade (Repository repository,
                         PersonMapper personMapper,
                         QueryMapper queryMapper,
                         TeamValidator teamValidator) {
        this.repository = repository;
        this.personMapper = personMapper;
        this.queryMapper = queryMapper;
        this.teamValidator = teamValidator;
    }

    public void delegateNewUserToRepository(UserModel userModel) {
        Team userModelsTeam = getUserModelTeamFromRepository(userModel);
        Person person = personMapper.map(userModel, userModelsTeam);
        repository.addPerson(person);
    }

    public boolean validateModelsTeamExists(UserModel newUser) {
        String teamName = newUser.team;
        return teamValidator.validate(teamName);
    }

    protected Team getUserModelTeamFromRepository(UserModel newUser) {
        Query teamQuery = queryMapper.map(newUser.team);
        return repository.queryTeams(teamQuery).get(0);
    }
}
