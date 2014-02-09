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
                description.appendText(String.format("whose name is %s", name));
            }
        });
        return this;
    }
}