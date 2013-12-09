package facade;

import data.Repository;
import facade.validators.TeamValidator;
import requestmapper.PersonMapper;
import requestmapper.QueryMapper;

public class FacadeCreator {
    public static SignUpFacade createSignUpFacade(Repository repository) {
        QueryMapper queryMapper = new QueryMapper();
        PersonMapper personMapper = new PersonMapper();
        TeamValidator teamValidator = new TeamValidator(repository, queryMapper);

        return new SignUpFacade(repository, personMapper, queryMapper, teamValidator);
    }
}
