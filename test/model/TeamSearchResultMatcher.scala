package model

import util.CompositeMatcher
import org.hamcrest.{TypeSafeMatcher, Description}

object TeamSearchResultMatcher {
    def aTeamSearchResult = new TeamSearchResultMatcher()
}

class TeamSearchResultMatcher extends CompositeMatcher[TeamSearchResult] {
    val typeName = "a team search result"

    def named(name: String): TeamSearchResultMatcher = {
        add(new TypeSafeMatcher[TeamSearchResult] () {
            def matchesSafely(team: TeamSearchResult): Boolean = team.name == name

            def describeTo(description: Description) {
                description.appendText(s"named $name")
            }
        })
        this
    }
}
