package util;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Iterator;

public class CollectionMatcher <T> {
    public static <T> Matcher<Iterable<T>> containsExhaustively(final Matcher<T>... itemMatchers) {
        return new TypeSafeMatcher<Iterable<T>> () {
            @Override public boolean matchesSafely(Iterable<T> targetIterable) {
                boolean result = true;
                Iterator<T> iterator = targetIterable.iterator();

                for (Matcher<T> itemMatcher : itemMatchers) {
                    result = result && itemMatcher.matches(iterator.next());
                }
                return result && !iterator.hasNext();
            }

            @Override public void describeTo(Description description) {
                description.appendText("which contains exhaustively:\n");
                for (Matcher matcher : itemMatchers) {
                    description.appendDescriptionOf(matcher);
                    description.appendText("\n\n");
                }
            }
        };
    }
}
