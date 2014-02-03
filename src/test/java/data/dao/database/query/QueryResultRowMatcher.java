package data.dao.database.query;

import data.dao.database.QueryResultRow;
import data.dto.PersonInformation;
import data.dto.TeamInformation;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class QueryResultRowMatcher extends CompositeMatcher<QueryResultRow> {

    private QueryResultRowMatcher() {}

    public static QueryResultRowMatcher aRow() {
        return new QueryResultRowMatcher();
    }

    public QueryResultRowMatcher withPersonInformation(final Matcher<PersonInformation> personInformationMatcher) {
        add(new TypeSafeMatcher<QueryResultRow>() {
            @Override
            public boolean matchesSafely(QueryResultRow queryResultRow) {
                return personInformationMatcher.matches(queryResultRow.getPerson());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" with person: ").appendDescriptionOf(personInformationMatcher);
            }
        });
        return this;
    }

    public QueryResultRowMatcher withTeamInformation(final Matcher<TeamInformation> teamInformationMatcher) {
        add(new TypeSafeMatcher<QueryResultRow>() {
            @Override
            public boolean matchesSafely(QueryResultRow queryResultRow) {
                return teamInformationMatcher.matches(queryResultRow.getTeam());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" with team: ").appendDescriptionOf(teamInformationMatcher);
            }
        });
        return this;
    }
}
