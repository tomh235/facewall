package uk.co.o2.facewall.data.dao.database;

import uk.co.o2.facewall.data.dao.database.query.DatabaseQuery;
import uk.co.o2.facewall.data.dao.database.query.DatabaseQueryBuilder;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

public class DatabaseQueryBuilderMatcher extends CompositeMatcher<DatabaseQueryBuilder> {

    private DatabaseQueryBuilderMatcher() {}

    public static DatabaseQueryBuilderMatcher aDatabaseQueryBuilder() {
        return new DatabaseQueryBuilderMatcher();
    }

    public DatabaseQueryBuilderMatcher whichBuilds(final Matcher<DatabaseQuery> matcher) {
        add(new TypeSafeMatcher<DatabaseQueryBuilder>() {
            @Override public boolean matchesSafely(DatabaseQueryBuilder databaseQueryBuilder) {
                return matcher.matches(databaseQueryBuilder.build());
            }

            @Override public void describeTo(Description description) {
                description.appendText("which builds a query matching: \n").appendDescriptionOf(matcher);
            }
        });
        return this;
    }
}
