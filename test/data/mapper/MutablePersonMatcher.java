package data.mapper;

import domain.Team;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class MutablePersonMatcher extends CompositeMatcher<MutablePerson> {

    private MutablePersonMatcher() {}

    public static MutablePersonMatcher aMutablePerson() {
        return new MutablePersonMatcher();
    }

    public MutablePersonMatcher whoseTeamHasBeenSetTo(final Matcher<Team> teamMatcher) {
        add(new TypeSafeMatcher<MutablePerson>() {
            @Override public boolean matchesSafely(MutablePerson mutablePerson) {
                return teamMatcher.matches(mutablePerson.team());
            }

            @Override public void describeTo(Description description) {
                description.appendText("whose team has been set to: ").appendDescriptionOf(teamMatcher);
            }
        });
        return this;
    }
}
