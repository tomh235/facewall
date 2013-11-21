package model;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class OverviewEntryModelMatcher extends CompositeMatcher<OverviewEntryModel> {

    private OverviewEntryModelMatcher(){
        super();
    }

    public static OverviewEntryModelMatcher anOverviewEntryModel() {
        return new OverviewEntryModelMatcher();
    }

    public OverviewEntryModelMatcher withTeamHeader(final String teamHeader) {
        add(new TypeSafeMatcher<OverviewEntryModel>() {

            @Override
            public boolean matchesSafely(OverviewEntryModel target) {
                return target.teamHeader.equals(teamHeader);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with a teamHead of " + teamHeader);
            }
        });
        return this;
    }

    public OverviewEntryModelMatcher named(final String name) {
        add(new TypeSafeMatcher<OverviewEntryModel>() {

            @Override
            public boolean matchesSafely(OverviewEntryModel target) {
                return target.name.equals(name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with name " + name);
            }
        });
        return this;
    }

    public OverviewEntryModelMatcher withPicture(final String picture) {
        add(new TypeSafeMatcher<OverviewEntryModel>() {

            @Override
            public boolean matchesSafely(OverviewEntryModel target) {
                return target.picture.equals(picture);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with has picture " + picture);
            }
        });
        return this;
    }

    public OverviewEntryModelMatcher withColour(final String colour) {
        add(new TypeSafeMatcher<OverviewEntryModel>() {

            @Override
            public boolean matchesSafely(OverviewEntryModel target) {
                return target.colour.equals(colour);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with has colour " + colour);
            }
        });
        return this;
    }
}
