package uk.co.o2.facewall.domain;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

import java.util.List;

import static uk.co.o2.facewall.util.IterableMatchers.contains;
import static uk.co.o2.facewall.util.IterableMatchers.containsExhaustivelyInAnyOrder;

public class PersonsMatcher extends CompositeMatcher<List<Person>> {

    private PersonsMatcher() {}

    public static PersonsMatcher arePersons() {
        return new PersonsMatcher();
    }

    public PersonsMatcher whichContainExhaustively(final Matcher<Person>... matchers) {
        add(new TypeSafeMatcher<List<Person>>() {
            private Matcher<Iterable<Person>> iterableMatcher = containsExhaustivelyInAnyOrder(matchers);

            @Override public boolean matchesSafely(List<Person> persons) {
                return iterableMatcher.matches(persons);
            }

            @Override public void describeTo(Description description) {
                description.appendDescriptionOf(iterableMatcher);
            }
        });
        return this;
    }

    public PersonsMatcher numbering(final int count) {
        add(new TypeSafeMatcher<List<Person>>() {
            @Override public boolean matchesSafely(List<Person> persons) {
                return count == persons.size();
            }

            @Override public void describeTo(Description description) {
                description.appendText(" which contains ").appendValue(count).appendText(" persons");
            }
        });
        return this;
    }

    public PersonsMatcher whichContains(final Matcher<Person> personMatcher) {
        add(new TypeSafeMatcher<List<Person>>() {
            @Override public boolean matchesSafely(List<Person> persons) {
                return contains(personMatcher).matches(persons);
            }

            @Override public void describeTo(Description description) {
                description.appendText(" contains a person which is ").appendDescriptionOf(personMatcher);
            }
        });
        return this;
    }
}
