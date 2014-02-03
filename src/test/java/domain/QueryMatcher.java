package domain;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class QueryMatcher extends CompositeMatcher<Query> {

    private QueryMatcher(){
        super();
    }

    public static QueryMatcher aQuery() {
        return new QueryMatcher();
    }

    public QueryMatcher withQueryString(final String queryString) {
        add(new TypeSafeMatcher<Query>() {
            @Override
            public boolean matchesSafely(Query query){
                return query.queryString().value.equals(queryString);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("for the regex pattern ").appendValue(queryString);
            }
        });
        return this;
    }

    public QueryMatcher whichIsEmpty() {
        add(new TypeSafeMatcher<Query>() {
            @Override
            public boolean matchesSafely(Query query){
                return query.queryString().value.equals("");
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("which is empty");
            }
        });
        return this;
    }
}
