package domain

import util.CompositeMatcher
import org.hamcrest.{Matcher, Description, TypeSafeMatcher}

@deprecated("deprecated, use java Teammatcher", "18/11/13")
class ScalaTeamMatcher extends CompositeMatcher[Team] {
    def withId(id: String) = {
        add(new TypeSafeMatcher[Team] {
            def matchesSafely(target: Team): Boolean = target.id == id

            def describeTo(description: Description) {
                description.appendText(s"with an id of $id")
            }
        })
        this
    }

    def named(name: String) = {
        add(new TypeSafeMatcher[Team] {
            def matchesSafely(target: Team): Boolean = target.name == name

            def describeTo(description: Description) {
                description.appendText(s"named $name")
            }
        })
        this
    }

    def withColour(colour: String) = {
        add(new TypeSafeMatcher[Team] {
            def matchesSafely(target: Team): Boolean = target.colour == colour

            def describeTo(description: Description) {
                description.appendText(s"coloured $colour")
            }
        })
        this
    }

    def whereMembers(members: Matcher[Iterable[Person]]) = {
        add(new TypeSafeMatcher[Team] {
            def matchesSafely(target: Team): Boolean = members.matches(target.members)

            def describeTo(description: Description) {
                description.appendText("where the members ")
                members.describeTo(description)
            }
        })
        this
    }
}

@deprecated("deprecated, use java Teammatcher", "18/11/13")
object ScalaTeamMatcher {
    def aTeam: ScalaTeamMatcher = new ScalaTeamMatcher()
}
