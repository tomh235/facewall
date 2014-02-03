package domain;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

import static domain.NoTeam.noTeam;

public class PersonMatcher extends CompositeMatcher<Person> {

    private PersonMatcher(){
        super();
    }

    public static PersonMatcher aPerson() {
        return new PersonMatcher();
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

    public PersonMatcher withEmail(final String email) {
        add(new TypeSafeMatcher<Person>() {

            @Override
            public boolean matchesSafely(Person target) {
                return email.equals(target.email());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("whose email is at ").appendValue(email);
            }
        });
        return this;

    }

    public PersonMatcher withRole(final String role) {
        add(new TypeSafeMatcher<Person>() {

            @Override
            public boolean matchesSafely(Person target) {
                return role.equals(target.role());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("whose role is ").appendValue(role);
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

    public PersonMatcher whoIsNotInATeam() {
        add(new TypeSafeMatcher<Person>() {

            @Override
            public boolean matchesSafely(Person person) {
                return noTeam() == person.team();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("who does not belong to a team");
            }
        });
        return this;
    }
}
