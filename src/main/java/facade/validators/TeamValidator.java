package facade.validators;

import data.TeamRepository;
import domain.Query;

import static domain.Query.newCaseSensitiveQuery;

public class TeamValidator {
    private final TeamRepository repository;

    public TeamValidator(TeamRepository repository) {
        this.repository = repository;
    }

    public boolean validate(String teamName) {
        Query teamQuery = newCaseSensitiveQuery(teamName);
        return !repository.queryTeams(teamQuery).isEmpty();
    }
}
