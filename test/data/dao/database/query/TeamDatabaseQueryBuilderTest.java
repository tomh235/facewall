package data.dao.database.query;

import org.junit.Test;

import static data.dao.database.DatabaseQueryBuilderMatcher.aDatabaseQueryBuilder;
import static data.dao.database.DatabaseQueryMatchers.aQueryForAllTeams;
import static data.dao.database.query.TeamDatabaseQueryBuilder.forTeams;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TeamDatabaseQueryBuilderTest {

    @Test
    public void forTeams_builds_query_for_all_teams() {
        TeamDatabaseQueryBuilder result = forTeams();

        assertThat(result, is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForAllTeams())
        ));
    }

}
