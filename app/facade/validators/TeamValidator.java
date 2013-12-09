package facade.validators;

import data.Repository;
import data.ScalaRepository;
import domain.Query;
import requestmapper.QueryMapper;

public class TeamValidator {
    private final Repository repository;
    private final QueryMapper queryMapper;

    public TeamValidator(Repository repository, QueryMapper queryMapper) {
        this.repository = repository;
        this.queryMapper = queryMapper;
    }

    public boolean validate(String teamName) {
        Query teamQuery = queryMapper.map(teamName);

        return !repository.queryTeams(teamQuery).isEmpty();
    }
}
