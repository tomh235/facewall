package model;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class TeamSearchResultMatcher extends CompositeMatcher<TeamSearchResult> {
    private TeamSearchResultMatcher() {
        super();
    }

    public static TeamSearchResultMatcher aTeamSearchResult() {
        return new TeamSearchResultMatcher();
    }

    public TeamSearchResultMatcher named(final String name) {
        add(new TypeSafeMatcher<TeamSearchResult>() {
            @Override
            public boolean matchesSafely(TeamSearchResult target) {
                return target.name.equals(name);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose name is %s", name));
            }
        });
        return this;
    }
}
