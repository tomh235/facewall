package util

import org.hamcrest.{Description, TypeSafeMatcher, Matcher}

object CollectionMatcher {
    def contains[T](elementMatchers: Matcher[T]*): Matcher[Iterable[T]] = new TypeSafeMatcher[Iterable[T]]() {
        def matchesSafely(targets: Iterable[T]): Boolean = targets.size == elementMatchers.size &&
            (targets, elementMatchers).zipped.forall { (target, matcher) => matcher.matches(target) }

        def describeTo(description: Description) {
            description.appendText("should contain -\n")
        }
    }
}
