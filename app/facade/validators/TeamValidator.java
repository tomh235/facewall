package facade.validators;

import data.Repository;
import domain.Query;

import static domain.Query.newQuery;

public class TeamValidator {
    private final Repository repository;

    public TeamValidator(Repository repository) {
        this.repository = repository;
    }

    public boolean validate(String teamName) {
        Query teamQuery = newQuery(teamName);
        return !repository.queryTeams(teamQuery).isEmpty();
    }
}
