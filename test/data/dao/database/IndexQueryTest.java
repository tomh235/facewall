package data.dao.database;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import static data.dao.database.IndexQuery.anIndexLookup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IndexQueryTest {
    private static final Matcher<IndexQuery> nullSafeIndexQuery = new TypeSafeMatcher<IndexQuery>() {
        @Override public boolean matchesSafely(IndexQuery indexQuery) {
            return (indexQuery.indexName != null) &&
                (indexQuery.keyName != null) &&
                (indexQuery.queriedValue != null);
        }

        @Override public void describeTo(Description description) {
            description.appendText("an IndexQuery where none of the fields are null.");
        }
    };

    @Test
    public void null_safe_builder() {
        IndexQuery indexQuery = anIndexLookup().build();

        assertThat(indexQuery, is(nullSafeIndexQuery));
    }
}
