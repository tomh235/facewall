package util

import org.hamcrest.{Description, TypeSafeMatcher}
import scala.collection.mutable

abstract class CompositeMatcher[T] extends TypeSafeMatcher[T] {
    final private var matchers: mutable.MutableList[TypeSafeMatcher[T]] = mutable.MutableList.empty[TypeSafeMatcher[T]]
    protected val typeName: String

    protected final def add(matcher: TypeSafeMatcher[T]) = {
        matchers += matcher
    }

    final def matchesSafely(target: T): Boolean = matchers.forall {
        matcher => matcher.matchesSafely(target)
    }

    final def describeTo(description: Description) {
        description.appendText(s"$typeName ")
        matchers.foreach { matcher =>
            matcher.describeTo(description)
            description.appendText("\n")
        }
    }
}
