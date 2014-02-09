package uk.co.o2.facewall.model;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

public class PersonSearchResultMatcher extends CompositeMatcher<PersonSearchResult> {
    private PersonSearchResultMatcher() {
        super();
    }

    public static PersonSearchResultMatcher aPersonSearchResult() {
        return new PersonSearchResultMatcher();
    }

    public PersonSearchResultMatcher named(final String name) {
        add(new TypeSafeMatcher<PersonSearchResult>() {
            @Override
            public boolean matchesSafely(PersonSearchResult target) {
                return target.name.equals(name);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose name is %s", name));
            }
        });
        return this;
    }

    public PersonSearchResultMatcher withPicture(final String picture) {
        add(new TypeSafeMatcher<PersonSearchResult>() {
            @Override
            public boolean matchesSafely(PersonSearchResult target) {
                return target.picture.equals(picture);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose has picture  %s", picture));
            }
        });
        return this;
    }

    public PersonSearchResultMatcher withEmail(final String email) {
        add(new TypeSafeMatcher<PersonSearchResult>() {
            @Override
            public boolean matchesSafely(PersonSearchResult target) {
                return target.email.equals(email);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose has email  %s", email));
            }
        });
        return this;
    }

    public PersonSearchResultMatcher withRole(final String role) {
        add(new TypeSafeMatcher<PersonSearchResult>() {
            @Override
            public boolean matchesSafely(PersonSearchResult target) {
                return target.role.equals(role);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose has role  %s", role));
            }
        });
        return this;
    }

    public PersonSearchResultMatcher inTeam(final String teamName) {
        add(new TypeSafeMatcher<PersonSearchResult>() {
            @Override
            public boolean matchesSafely(PersonSearchResult target) {
                return target.teamName.equals(teamName);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("whose team is named %s", teamName));
            }
        });
        return this;
    }
}
