package data.dao.database.query;

import data.datatype.TeamId;

public class TeamDatabaseQueryBuilder implements DatabaseQueryBuilder {

    private String id = "*";

    private TeamDatabaseQueryBuilder() {}

    public static TeamDatabaseQueryBuilder forTeams() {
        return new TeamDatabaseQueryBuilder();
    }

    public TeamDatabaseQueryBuilder withId(TeamId id) {
        this.id = id.value;
        return this;
    }

    public DatabaseQuery build() {
        return new TeamDatabaseQuery(id);
    }
}
