package domain;

import util.CompositeMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class PersonMatcher extends CompositeMatcher<Person> {

    private PersonMatcher(){
        super();
    }

    public static PersonMatcher aPerson() {
        return new PersonMatcher();
    }

    public PersonMatcher withId(final String id) {
        add(new TypeSafeMatcher<Person>() {

            @Override
            public boolean matchesSafely(Person target) {
                return target.id().equals(id);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with an id of " + id);
            }
        });
        return this;
    }

    public PersonMatcher named(final String name) {
        add(new TypeSafeMatcher<Person>() {

            @Override
            public boolean matchesSafely(Person target) {
                return target.name().equals(name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with name " + name);
            }
        });
        return this;
    }

    public PersonMatcher withPicture(final String picture) {
        add(new TypeSafeMatcher<Person>() {

            @Override
            public boolean matchesSafely(Person target) {
                return target.picture().equals(picture);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with has colour " + picture);
            }
        });
        return this;

    }    public PersonMatcher inTeam(final TeamMatcher team) {
        add(new TypeSafeMatcher<Person>() {

            @Override
            public boolean matchesSafely(Person target) {
                return target.team().equals(team);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("in the team " + team);
            }
        });
        return this;
    }
}
