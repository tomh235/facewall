package util

import org.hamcrest.{Description, Matcher}
import org.junit.internal.matchers.TypeSafeMatcher

object ScalaCollectionMatcher {
    def contains[T](elementMatchers: Matcher[T]*): Matcher[Iterable[T]] = new TypeSafeMatcher[Iterable[T]]() {
        def matchesSafely(targets: Iterable[T]): Boolean = targets.size == elementMatchers.size &&
            (targets, elementMatchers).zipped.forall {
                (target, matcher) => matcher.matches(target)
            }

        def describeTo(description: Description) {
            description.appendText("should contain: -\n")
            description.appendDescriptionOf(elementMatchers.headOption.getOrElse { return })
            elementMatchers.tail.foreach { matcher =>
                description.appendText("\n,\n")
                description.appendDescriptionOf(matcher)
            }
            description.appendText("\n.")
        }
    }
}
