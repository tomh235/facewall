package facade.validators;

import data.ScalaRepository;
import domain.Query;
import requestmapper.QueryMapper;

public class TeamValidator {
    ScalaRepository repository;
    QueryMapper queryMapper;

    public TeamValidator(ScalaRepository repository, QueryMapper queryMapper) {
        this.repository = repository;
        this.queryMapper = queryMapper;
    }

    public boolean validate(String teamName) {
        Query teamQuery = queryMapper.map(teamName);

        return !repository.queryTeams(teamQuery).isEmpty();
    }
}
