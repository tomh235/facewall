package facade;

import data.ScalaRepository;
import facade.validators.TeamValidator;
import requestmapper.PersonMapper;
import requestmapper.QueryMapper;

public class FacadeCreator {
    public static SignUpFacade createSignUpFacade(ScalaRepository repository) {
        QueryMapper queryMapper = new QueryMapper();
        PersonMapper personMapper = new PersonMapper(repository);
        TeamValidator teamValidator = new TeamValidator(repository, queryMapper);

        return new SignUpFacade(repository, personMapper, queryMapper, teamValidator);
    }
}
