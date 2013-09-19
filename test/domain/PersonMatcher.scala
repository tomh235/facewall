package domain

import util.CompositeMatcher
import org.hamcrest.{Matcher, Description, TypeSafeMatcher}

object PersonMatcher {
    def aPerson = new PersonMatcher()
}

class PersonMatcher extends CompositeMatcher[Person] {
    override val typeName = "a Person"

    def withId(id: String) = {
        add(new TypeSafeMatcher[Person]{
            def matchesSafely(target: Person): Boolean = target.id == id

            def describeTo(description: Description) {
                description.appendText(s"with an id of $id")
            }
        })
        this
    }

    def named(name: String) = {
        add(new TypeSafeMatcher[Person]{
            def matchesSafely(target: Person): Boolean = target.name == name

            def describeTo(description: Description) {
                description.appendText(s"named $name")
            }
        })
        this
    }

    def withPicture(picture: String) = {
        add(new TypeSafeMatcher[Person]{
            def matchesSafely(target: Person): Boolean = target.picture == picture

            def describeTo(description: Description) {
                description.appendText(s"whose picture is $picture")
            }
        })
        this
    }

    def inTeam(team: Matcher[Team]) = {
        add(new TypeSafeMatcher[Person]{
            def matchesSafely(target: Person): Boolean = team.matches(target.team.getOrElse { return false } )

            def describeTo(description: Description) {
                description.appendText(s"whose team matches ")
                team.describeTo(description)
            }
        })
        this
    }
}
