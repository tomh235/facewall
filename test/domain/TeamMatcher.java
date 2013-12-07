package domain;

import util.CompositeMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class TeamMatcher extends CompositeMatcher<Team> {

    private TeamMatcher(){
        super();
    }

    public static TeamMatcher aTeam() {
        return new TeamMatcher();
    }

    public TeamMatcher withId(final String id) {
        add(new TypeSafeMatcher<Team>() {

            @Override
            public boolean matchesSafely(Team target) {
                return target.id().equals(id);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with an id of " + id);
            }
        });
        return this;
    }

    public TeamMatcher named(final String name) {
        add(new TypeSafeMatcher<Team>() {

            @Override
            public boolean matchesSafely(Team target) {
                return target.name().equals(name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with name " + name);
            }
        });
        return this;
    }

    public TeamMatcher withColour(final String colour) {
        add(new TypeSafeMatcher<Team>() {

            @Override
            public boolean matchesSafely(Team target) {
                return target.colour().equals(colour);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with has colour " + colour);
            }
        });
        return this;
    }

    public TeamMatcher whereMembers(final Matcher<Iterable<Person>> members) {
        add(new TypeSafeMatcher<Team>() {

            @Override
            public boolean matchesSafely(Team target) {
                return members.matches(target.members());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with members " + members);
            }
        });
        return this;
    }
}
