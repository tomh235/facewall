package data.dao.database.query;

import data.datatype.TeamId;
import org.junit.Test;

import static data.dao.database.DatabaseQueryBuilderMatcher.aDatabaseQueryBuilder;
import static data.dao.database.DatabaseQueryMatchers.aQueryForAllTeams;
import static data.dao.database.DatabaseQueryMatchers.aQueryForTeamWithId;
import static data.dao.database.query.TeamDatabaseQueryBuilder.forTeams;
import static data.datatype.TeamId.newTeamId;
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

    @Test
    public void builds_query_for_team_with_id() {
        TeamDatabaseQueryBuilder result = forTeams().withId(newTeamId("identifier"));

        assertThat(result, is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForTeamWithId("identifier"))
        ));
    }

}
