package model

import util.CompositeMatcher
import org.hamcrest.{Description, TypeSafeMatcher}

class PersonSearchResultMatcher extends CompositeMatcher[PersonSearchResult] {
    def named(name: String): PersonSearchResultMatcher = {
        add(new TypeSafeMatcher[PersonSearchResult] {
          def matchesSafely(target: PersonSearchResult): Boolean = target.name == name

          def describeTo(description: Description) { description.appendText(s"named $name")}
        })
        this
    }

    def withPicture(picture: String): PersonSearchResultMatcher = {
        add(new TypeSafeMatcher[PersonSearchResult] {
          def matchesSafely(target: PersonSearchResult): Boolean = target.picture == picture

          def describeTo(description: Description) { description.appendText(s"with picture $picture")}
        })
        this
    }

    def inTeam(teamName: String): PersonSearchResultMatcher = {
        add(new TypeSafeMatcher[PersonSearchResult] {
          def matchesSafely(target: PersonSearchResult): Boolean = target.teamName == teamName

          def describeTo(description: Description) { description.appendText(s"in the team, $teamName")}
        })
        this
    }
}

object PersonSearchResultMatcher {
    def aPersonSearchResult: PersonSearchResultMatcher = new PersonSearchResultMatcher()
}
