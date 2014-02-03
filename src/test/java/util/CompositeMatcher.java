package util;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeMatcher <T> extends TypeSafeMatcher<T> {
    final private List<TypeSafeMatcher<T>> matchers = new ArrayList<>();
    protected String typeName = this.getClass().getSimpleName().replaceAll("Matcher", "");

    protected final void add(TypeSafeMatcher<T> matcher) {
        matchers.add(matcher);
    }

    @Override
    public final boolean matchesSafely(T target) {
        boolean result = true;

        for (TypeSafeMatcher<T> matcher : matchers) {
            result = result && matcher.matches(target);
        }
        return result;
    }

    @Override
    final public void describeTo(Description description) {
        description.appendText(typeName + " ");
        for (TypeSafeMatcher<T> matcher : matchers) {
            description.appendText("\n");
            description.appendDescriptionOf(matcher);
        }
    }
}
