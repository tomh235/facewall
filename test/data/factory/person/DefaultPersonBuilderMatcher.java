package data.factory.person;

import domain.Team;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class DefaultPersonBuilderMatcher extends CompositeMatcher<DefaultPersonBuilder> {

    private DefaultPersonBuilderMatcher() {}

    public static DefaultPersonBuilderMatcher aDefaultPersonBuilder() {
        return new DefaultPersonBuilderMatcher();
    }

    public DefaultPersonBuilderMatcher withTheFollowingTeamSet(final Matcher<Team> teamMatcher) {
        add(new TypeSafeMatcher<DefaultPersonBuilder>() {
            @Override public boolean matchesSafely(DefaultPersonBuilder defaultPersonBuilder) {
                return teamMatcher.matches(defaultPersonBuilder.build().team());
            }

            @Override public void describeTo(Description description) {
                description.appendText("where the team has been set to ").appendDescriptionOf(teamMatcher);
            }
        });
        return this;
    }
}
