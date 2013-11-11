package util;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class CollectionMatcher <T> {
    public static <T> Matcher<List<T>> contains(final Matcher<T>... itemMatchers) {
        return new TypeSafeMatcher<List<T>> () {
            @Override public boolean matchesSafely(List<T> targetList) {
                boolean result = itemMatchers.length == targetList.size();
                for(int index = 0; index < itemMatchers.length; index++) {
                    result = result && itemMatchers[index].matches(targetList.get(index));
                }
                return result;
            }

            @Override public void describeTo(Description description) {
                description.appendText("a list which contains:\n");
                for (Matcher matcher : itemMatchers) {
                    description.appendDescriptionOf(matcher);
                    description.appendText("\n\n");
                }
            }
        };
    }
}
