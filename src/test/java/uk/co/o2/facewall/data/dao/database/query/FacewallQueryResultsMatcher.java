package uk.co.o2.facewall.data.dao.database.query;

import uk.co.o2.facewall.data.dao.database.QueryResultRow;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

import java.util.Iterator;

import static uk.co.o2.facewall.util.IterableMatchers.contains;

public class FacewallQueryResultsMatcher extends CompositeMatcher<Iterable<QueryResultRow>> {

    private FacewallQueryResultsMatcher() {}

    public static FacewallQueryResultsMatcher areQueryResults() {
        return new FacewallQueryResultsMatcher();
    }

    public FacewallQueryResultsMatcher containingARow(Matcher<QueryResultRow> rowMatcher) {
        final Matcher<Iterable<QueryResultRow>> resultsMatcher = contains(rowMatcher);
        add(new TypeSafeMatcher<Iterable<QueryResultRow>>() {
            @Override
            public boolean matchesSafely(Iterable<QueryResultRow> queryResultRows) {
                return resultsMatcher.matches(queryResultRows);
            }

            @Override
            public void describeTo(Description description) {
                description.appendDescriptionOf(resultsMatcher);
            }
        });
        return this;
    }

    public FacewallQueryResultsMatcher withRowCount(final int expectedCount) {
        add(new TypeSafeMatcher<Iterable<QueryResultRow>>() {
            @Override
            public boolean matchesSafely(Iterable<QueryResultRow> queryResultRows) {
                int rowCount = 0;
                for (Iterator iterator = queryResultRows.iterator(); iterator.hasNext(); iterator.next()) {
                    rowCount++;
                }

                return expectedCount == rowCount;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" with ").appendValue(expectedCount).appendText(" rows.");
            }
        });
        return this;    }
}
