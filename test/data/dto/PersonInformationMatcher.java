package data.dto;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

import static data.datatype.PersonId.newPersonId;

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
}
