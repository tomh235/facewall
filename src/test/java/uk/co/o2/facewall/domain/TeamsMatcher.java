package uk.co.o2.facewall.domain;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

import java.util.List;

import static uk.co.o2.facewall.util.IterableMatchers.containsExhaustivelyInAnyOrder;

public class TeamsMatcher extends CompositeMatcher<List<Team>> {

    private TeamsMatcher() {}

    public static TeamsMatcher areTeams() {
        return new TeamsMatcher();
    }

    public TeamsMatcher numbering(final int count) {
        add(new TypeSafeMatcher<List<Team>>() {
            @Override public boolean matchesSafely(List<Team> teams) {
                return count == teams.size();
            }

            @Override public void describeTo(Description description) {
                description.appendText(" which contains ").appendValue(count).appendText(" teams");
            }
        });
        return this;
    }

    public TeamsMatcher whichContains(final Matcher<Team> teamMatcher) {
        add(new TypeSafeMatcher<List<Team>>() {
            @Override public boolean matchesSafely(List<Team> teams) {
                boolean result = false;

                for (Team team : teams) {
                    result = result || teamMatcher.matches(team);
                }
                return result;
            }

            @Override public void describeTo(Description description) {
                description.appendText(" contains a team which is ").appendDescriptionOf(teamMatcher);
            }
        });
        return this;
    }

    public TeamsMatcher whichContainExhaustively(final Matcher<Team>... teamMatchers) {
        add(new TypeSafeMatcher<List<Team>>() {
            Matcher<Iterable<Team>> listMatcher = containsExhaustivelyInAnyOrder(teamMatchers);

            @Override public boolean matchesSafely(List<Team> teams) {
                return listMatcher.matches(teams);
            }

            @Override public void describeTo(Description description) {
                description.appendDescriptionOf(listMatcher);
            }
        });
        return this;
    }
}
