package uk.co.o2.facewall.data.dao.database.query;

import org.junit.Test;

import static uk.co.o2.facewall.data.dao.database.DatabaseQueryBuilderMatcher.aDatabaseQueryBuilder;
import static uk.co.o2.facewall.data.dao.database.DatabaseQueryMatchers.aQueryForAllTeams;
import static uk.co.o2.facewall.data.dao.database.DatabaseQueryMatchers.aQueryForTeamWithId;
import static uk.co.o2.facewall.data.datatype.TeamId.newTeamId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class TeamDatabaseQueryBuilderTest {

    private final DatabaseQueryFactory databaseQueryFactory = new DatabaseQueryFactory(mock(FacewallQueryResultsMapper.class));

    @Test
    public void forTeams_builds_query_for_all_teams() {
        TeamDatabaseQueryBuilder result = databaseQueryFactory.forTeams();

        assertThat(result, is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForAllTeams())
        ));
    }

    @Test
    public void builds_query_for_team_with_id() {
        TeamDatabaseQueryBuilder result = databaseQueryFactory.forTeams().withId(newTeamId("identifier"));

        assertThat(result, is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForTeamWithId("identifier"))
        ));
    }

}
