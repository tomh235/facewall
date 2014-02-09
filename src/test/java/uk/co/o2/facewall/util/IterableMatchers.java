package uk.co.o2.facewall.util;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Iterator;

public class IterableMatchers<T> {

    private IterableMatchers() {}

    public static <T> Matcher<Iterable<T>> containsExhaustivelyInOrder(final Matcher<T>... itemMatchers) {
        return new TypeSafeMatcher<Iterable<T>> () {
            @Override public boolean matchesSafely(Iterable<T> targetIterable) {
                boolean result = true;
                Iterator<T> iterator = targetIterable.iterator();

                for (Matcher<T> itemMatcher : itemMatchers) {
                    if (iterator.hasNext()){
                        T nextItem = iterator.next();

                        result = result && itemMatcher.matches(nextItem);
                    } else {
                        result = false;
                    }
                }
                return result && !iterator.hasNext();
            }

            @Override public void describeTo(Description description) {
                description.appendText("which contains exhaustively, in order:\n");
                for (Matcher matcher : itemMatchers) {
                    description.appendDescriptionOf(matcher);
                    description.appendText("\n\n");
                }
            }
        };
    }

    public static <T> Matcher<Iterable<T>> containsExhaustivelyInAnyOrder(final Matcher<T>... matchers) {
        return new TypeSafeMatcher<Iterable<T>>() {

            @Override public boolean matchesSafely(Iterable<T> iterable) {
                boolean result = true;

                for(Matcher<T> matcher : matchers) {
                    result = result && contains(matcher).matches(iterable);
                }
                return result && hasElementCount(matchers.length).matches(iterable);
            }

            @Override public void describeTo(Description description) {
                description.appendText("which contains exhaustively, in any order:\n");
                for (Matcher matcher : matchers) {
                    description.appendDescriptionOf(matcher);
                    description.appendText("\n\n");
                }
            }
        };
    }

    public static <T> Matcher<Iterable<T>> contains(final Matcher<T> matcher) {
        return new TypeSafeMatcher<Iterable<T>>() {
            @Override public boolean matchesSafely(Iterable<T> iterable) {
                boolean result = false;

                for (T item : iterable) {
                    result = result || matcher.matches(item);
                }
                return result;
            }

            @Override public void describeTo(Description description) {
                description.appendText("which contains ").appendDescriptionOf(matcher);
            }
        };
    }

    public static <T> Matcher<Iterable<T>> hasElementCount(final int expectedCount) {
        return new TypeSafeMatcher<Iterable<T>>() {
            @Override public boolean matchesSafely(Iterable<T> iterable) {
                int elementCount = 0;

                for (T ignored : iterable) {
                    elementCount++;
                }
                return expectedCount == elementCount;
            }

            @Override public void describeTo(Description description) {
                description.appendText("which contains ").appendValue(expectedCount).appendText(" elements");
            }
        };
    }
}
