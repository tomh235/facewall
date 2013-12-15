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

    public QueryMatcher withRegEx(final String regEx) {
        add(new TypeSafeMatcher<Query>() {
            @Override
            public boolean matchesSafely(Query query){
                return query.queryString().value.equals(regEx);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Where the target is " + regEx);
            }
        });
        return this;
    }
}
