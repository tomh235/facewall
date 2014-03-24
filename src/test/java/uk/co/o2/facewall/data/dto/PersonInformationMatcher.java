package uk.co.o2.facewall.data.dto;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;

public class PersonInformationMatcher extends CompositeMatcher<PersonInformation> {

    private PersonInformationMatcher() {}

    public static PersonInformationMatcher aPersonInformation() {
        return new PersonInformationMatcher();
    }

    public PersonInformationMatcher withId(final String id) {
        add(new TypeSafeMatcher<PersonInformation>() {
            @Override
            public boolean matchesSafely(PersonInformation personInformation) {
                return newPersonId(id).equals(personInformation.getId());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" with the id ").appendValue(id);
            }
        });
        return this;
    }

    public PersonInformationMatcher named(final String name) {
        add(new TypeSafeMatcher<PersonInformation>() {
            @Override
            public boolean matchesSafely(PersonInformation personInformation) {
                return name.equals(personInformation.getName());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" with the name ").appendValue(name);
            }
        });
        return this;
    }

    public PersonInformationMatcher withPicture(final String picture) {
        add(new TypeSafeMatcher<PersonInformation>() {
            @Override
            public boolean matchesSafely(PersonInformation personInformation) {
                return picture.equals(personInformation.getPicture());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" with the picture ").appendValue(picture);
            }
        });
        return this;
    }

    public PersonInformationMatcher withRole(final String role) {
        add(new TypeSafeMatcher<PersonInformation>() {
            @Override
            public boolean matchesSafely(PersonInformation personInformation) {
                return role.equals(personInformation.getRole());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" with the role ").appendValue(role);
            }
        });
        return this;
    }
}
