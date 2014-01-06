package facade.validators;

import data.TeamRepository;
import domain.Query;
import model.UserModel;

import static domain.Query.newQuery;

public class TeamValidator {
    private final TeamRepository repository;

    public TeamValidator(TeamRepository repository) {
        this.repository = repository;
    }

    public boolean validate(String teamName) {
        Query teamQuery = newQuery(teamName);
        return !repository.queryTeams(teamQuery).isEmpty();
    }
}
