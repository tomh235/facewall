package uk.co.o2.facewall.model;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

public class TeamDetailsModelMatcher extends CompositeMatcher<TeamDetailsModel> {

    private TeamDetailsModelMatcher() {
        super();
    }

    public static TeamDetailsModelMatcher aTeamDetailsModel() {
        return new TeamDetailsModelMatcher();
    }

    public TeamDetailsModelMatcher named(final String name) {
        add(new TypeSafeMatcher<TeamDetailsModel>() {
            @Override
            public boolean matchesSafely(TeamDetailsModel target) {
                return target.name.equals(name);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose name is %s", name));
            }
        });
        return this;
    }

    public TeamDetailsModelMatcher withColour(final String colour) {
        add(new TypeSafeMatcher<TeamDetailsModel>() {
            @Override
            public boolean matchesSafely(TeamDetailsModel target) {
                return target.colour.equals(colour);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose colour is %s", colour));
            }
        });
        return this;
    }
}