package uk.co.o2.facewall.model;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

import java.util.List;

public class TeamDetailsWithPersonsModelMatcher extends CompositeMatcher<TeamDetailsWithPersonsModel> {

    private TeamDetailsWithPersonsModelMatcher() {
        super();
    }

    public static TeamDetailsWithPersonsModelMatcher aTeamDetailsWithPersonsModel() {
        return new TeamDetailsWithPersonsModelMatcher();
    }

    public TeamDetailsWithPersonsModelMatcher named(final String name) {
        add(new TypeSafeMatcher<TeamDetailsWithPersonsModel>() {
            @Override
            public boolean matchesSafely(TeamDetailsWithPersonsModel target) {
                return target.name.equals(name);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose name is %s", name));
            }
        });
        return this;
    }

    public TeamDetailsWithPersonsModelMatcher withColour(final String colour) {
        add(new TypeSafeMatcher<TeamDetailsWithPersonsModel>() {
            @Override
            public boolean matchesSafely(TeamDetailsWithPersonsModel target) {
                return target.colour.equals(colour);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose colour is %s", colour));
            }
        });
        return this;
    }

    public TeamDetailsWithPersonsModelMatcher sized(final int size) {
        add(new TypeSafeMatcher<TeamDetailsWithPersonsModel>() {
            @Override
            public boolean matchesSafely(TeamDetailsWithPersonsModel target) {
                return target.size == size;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose size is %s", size));
            }
        });
        return this;
    }

    public TeamDetailsWithPersonsModelMatcher containing(final List<OverviewEntryModel> entries) {
        add(new TypeSafeMatcher<TeamDetailsWithPersonsModel>() {
            @Override
            public boolean matchesSafely(TeamDetailsWithPersonsModel target) {
                return target.entries.equals(entries);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("which contains entries: %s", entries.toString()));
            }
        });
        return this;
    }

}
