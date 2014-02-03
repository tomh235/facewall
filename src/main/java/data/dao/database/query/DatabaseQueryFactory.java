package data.dao.database.query;

public class DatabaseQueryFactory {

    private final FacewallQueryResultsMapper queryResultsMapper;

    public DatabaseQueryFactory(FacewallQueryResultsMapper queryResultsMapper) {
        this.queryResultsMapper = queryResultsMapper;
    }

    public PersonDatabaseQueryBuilder forPersons() {
        return new PersonDatabaseQueryBuilder(queryResultsMapper);
    }

    public TeamDatabaseQueryBuilder forTeams() {
        return new TeamDatabaseQueryBuilder(queryResultsMapper);
    }
}
