package uk.co.o2.facewall.facade.validators;

import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.domain.Query;

import static uk.co.o2.facewall.domain.Query.newCaseSensitiveQuery;

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
