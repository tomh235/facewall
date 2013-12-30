package data.dto;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class PersonDTOMatcher extends CompositeMatcher<PersonDTO> {
    private PersonDTOMatcher() {}

    public static PersonDTOMatcher aPersonDTO() {
        return new PersonDTOMatcher();
    }

    public PersonDTOMatcher withPerson(final Matcher<PersonInformation> personMatcher) {
        add(new TypeSafeMatcher<PersonDTO>() {
            @Override public boolean matchesSafely(PersonDTO personDTO) {
                return personMatcher.matches(personDTO.personInformation);
            }

            @Override public void describeTo(Description description) {
                description.appendText("with a person node matching: ").appendDescriptionOf(personMatcher);
            }
        });
        return this;
    }
    
    public PersonDTOMatcher withTeam(final Matcher<TeamInformation> teamMatcher) {
        add(new TypeSafeMatcher<PersonDTO>() {
            @Override public boolean matchesSafely(PersonDTO personDTO) {
                return teamMatcher.matches(personDTO.teamInformation);
            }

            @Override public void describeTo(Description description) {
                description.appendText("with a team node matching: ").appendDescriptionOf(teamMatcher);
            }
        });
        return this;
    }
}
