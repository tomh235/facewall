package domain;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import util.CompositeMatcher;

import static util.CollectionMatcher.containsExhaustively;

public class PersonsMatcher extends CompositeMatcher<Persons> {

    private PersonsMatcher() {}

    public static PersonsMatcher arePersons() {
        return new PersonsMatcher();
    }

    public PersonsMatcher whichContainExhaustively(final Matcher<Person>... matchers) {
        add(new TypeSafeMatcher<Persons>() {

            Matcher<Iterable<Person>> iterableMatcher = containsExhaustively(matchers);

            @Override public boolean matchesSafely(Persons persons) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override public void describeTo(Description description) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        return this;
    }
}
