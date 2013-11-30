package data.factory.team;

import data.mapper.MutableTeam;
import domain.Person;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

import java.util.List;

public class MutableTeamMatcher extends CompositeMatcher<MutableTeam> {

    private MutableTeamMatcher() {}

    public static MutableTeamMatcher aMutableTeam() {
        return new MutableTeamMatcher();
    }

    public MutableTeamMatcher whoseMembers(final Matcher<List<Person>> membersMatcher) {
        add(new TypeSafeMatcher<MutableTeam>() {
            @Override public boolean matchesSafely(MutableTeam mutableTeam) {
                return membersMatcher.matches(mutableTeam.members());
            }

            @Override public void describeTo(Description description) {
                description.appendText("whose members have been set to: ").appendDescriptionOf(membersMatcher);
            }
        });
        return this;
    }
}
