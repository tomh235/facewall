package uk.co.o2.facewall.model;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

public class PersonDetailsModelMatcher extends CompositeMatcher<PersonDetailsModel> {

    private PersonDetailsModelMatcher() {
        super();
    }

    public static PersonDetailsModelMatcher aPersonDetailsModel() {
        return new PersonDetailsModelMatcher();
    }

    public PersonDetailsModelMatcher named(final String name) {
        add(new TypeSafeMatcher<PersonDetailsModel>() {
            @Override
            public boolean matchesSafely(PersonDetailsModel target) {
                return target.name.equals(name);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("whose name is ").appendValue(name);
            }
        });
        return this;
    }

    public PersonDetailsModelMatcher withEmail(final String email) {
        add(new TypeSafeMatcher<PersonDetailsModel>() {
            @Override
            public boolean matchesSafely(PersonDetailsModel target) {
                return target.email.equals(email);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("whose email is ").appendValue(email);
            }
        });
        return this;
    }

    public PersonDetailsModelMatcher withPicture(final String picture) {
        add(new TypeSafeMatcher<PersonDetailsModel>() {
            @Override
            public boolean matchesSafely(PersonDetailsModel target) {
                return target.picture.equals(picture);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("whose picture is ").appendValue(picture);
            }
        });
        return this;
    }

    public PersonDetailsModelMatcher withRole(final String role) {
        add(new TypeSafeMatcher<PersonDetailsModel>() {
            @Override
            public boolean matchesSafely(PersonDetailsModel target) {
                return target.role.equals(role);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("whose role is ").appendValue(role);
            }
        });
        return this;
    }
}