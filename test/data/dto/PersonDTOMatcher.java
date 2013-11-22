package data.dto;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

public class PersonDTOMatcher extends CompositeMatcher<PersonDTO> {

    private PersonDTOMatcher() {}

    public static PersonDTOMatcher aPersonDTO() {
        return new PersonDTOMatcher();
    }

    public PersonDTOMatcher withId(final String id) {
        add(new TypeSafeMatcher<PersonDTO>() {
            @Override
            public boolean matchesSafely(PersonDTO dto) {
                return id.equals(dto.id);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with an Id of ").appendValue(id);
            }
        });
        return this;
    }

    public PersonDTOMatcher named(final String name) {
        add(new TypeSafeMatcher<PersonDTO>() {
            @Override
            public boolean matchesSafely(PersonDTO dto) {
                return name.equals(dto.name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("named ").appendValue(name);
            }
        });
        return this;
    }

    public PersonDTOMatcher whosePictureIs(final String picture) {
        add(new TypeSafeMatcher<PersonDTO>() {
            @Override
            public boolean matchesSafely(PersonDTO dto) {
                return picture.equals(dto.picture);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("whose picture is ").appendValue(picture);
            }
        });
        return this;
    }
}
