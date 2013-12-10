package data.dao.database;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class IndexQueryMatcher extends CompositeMatcher<IndexQuery> {
    private IndexQueryMatcher() {}

    public static IndexQueryMatcher anIndexQuery() {
        return new IndexQueryMatcher();
    }

    public IndexQueryMatcher queryingOnAnIndexNamed(final String indexName) {
        add(new TypeSafeMatcher<IndexQuery>() {
            @Override public boolean matchesSafely(IndexQuery indexQuery) {
                return indexName.equals(indexQuery.indexName);
            }

            @Override public void describeTo(Description description) {
                description.appendText(" querying on an index named ").appendValue(indexName);
            }
        });
        return this;
    }

    public IndexQueryMatcher queryingOnTheKey(final String keyName) {
        add(new TypeSafeMatcher<IndexQuery>() {
            @Override public boolean matchesSafely(IndexQuery indexQuery) {
                return keyName.equals(indexQuery.keyName);
            }

            @Override public void describeTo(Description description) {
                description.appendText(" querying on the key ").appendValue(keyName);
            }
        });
        return this;
    }

    public IndexQueryMatcher queryingForTheValue(final Object value) {
        add(new TypeSafeMatcher<IndexQuery>() {
            @Override public boolean matchesSafely(IndexQuery indexQuery) {
                return value.equals(indexQuery.queriedValue);
            }

            @Override public void describeTo(Description description) {
                description.appendText(" querying for ").appendValue(value);
            }
        });
        return this;
    }

    public IndexQueryMatcher queryingForAllValues() {
        add(new TypeSafeMatcher<IndexQuery>() {
            @Override public boolean matchesSafely(IndexQuery indexQuery) {
                return "*".equals(indexQuery.queriedValue);
            }

            @Override public void describeTo(Description description) {
                description.appendText(" querying for all values");
            }
        });
        return this;
    }
}
