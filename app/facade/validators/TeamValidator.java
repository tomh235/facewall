package facade.validators;

import data.Repository;
import domain.Query;

import static facade.QueryBuilder.newQuery;

public class TeamValidator {
    private final Repository repository;

    public TeamValidator(Repository repository) {
        this.repository = repository;
    }

    public boolean validate(String teamName) {
        Query teamQuery = newQuery().withKeywords(teamName).build();
        return !repository.queryTeams(teamQuery).isEmpty();
    }
}
