package model

import util.CompositeMatcher
import org.hamcrest.{Description, TypeSafeMatcher}

class PersonSearchResultMatcher extends CompositeMatcher[PersonSearchResult] {
    override val typeName = "a Person Search Result Model"

    def named(name: String): PersonSearchResultMatcher = {
        add(new TypeSafeMatcher[PersonSearchResult] {
          def matchesSafely(target: PersonSearchResult): Boolean = target.name == name

          def describeTo(description: Description) { description.appendText(s"named $name")}
        })
        this
    }
}

object PersonSearchResultMatcher {
    def aPersonSearchResult: PersonSearchResultMatcher = new PersonSearchResultMatcher()
}
