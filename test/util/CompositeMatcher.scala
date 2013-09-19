package util

import org.hamcrest.{Description, TypeSafeMatcher}
import scala.collection.mutable

class CompositeMatcher[T] extends TypeSafeMatcher[T] {
  protected var matchers: mutable.MutableList[TypeSafeMatcher[T]] = mutable.MutableList.empty[TypeSafeMatcher[T]]
  protected final def add(matcher: TypeSafeMatcher[T]) { matchers += matcher }

  def matchesSafely(target: T): Boolean = matchers.forall { matcher => matcher.matchesSafely(target) }

  def describeTo(description: Description) {
      matchers.foreach { matcher =>
          matcher.describeTo(description)
          description.appendText("\n")
      }
  }
}
