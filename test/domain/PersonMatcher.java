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
                return id.equals(target.id());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with an id of ").appendValue(id);
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
                description.appendText("named ").appendValue(name);
            }
        });
        return this;
    }

    public PersonMatcher withPicture(final String picture) {
        add(new TypeSafeMatcher<Person>() {

            @Override
            public boolean matchesSafely(Person target) {
                return picture.equals(target.picture());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("whose picture is at ").appendValue(picture);
            }
        });
        return this;

    }

    public PersonMatcher inTeam(final Matcher<Team> team) {
        add(new TypeSafeMatcher<Person>() {

            @Override
            public boolean matchesSafely(Person target) {
                return team.matches(target.team());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("in: ").appendDescriptionOf(team);
            }
        });
        return this;
    }
}
