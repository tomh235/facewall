package model

import util.CompositeMatcher
import org.hamcrest.{Description, TypeSafeMatcher}

object PersonDetailsModelMatcher {
    val aPersonDetailsModel = new PersonDetailsModelMatcher()
}

class PersonDetailsModelMatcher extends CompositeMatcher[PersonDetailsModel] {
    def named(name: String) = {
        add(new TypeSafeMatcher[PersonDetailsModel] {
            def matchesSafely(person: PersonDetailsModel): Boolean = {
                person.name == name
            }

            def describeTo(description: Description) {
                description.appendText(s"whose name is $name")
            }
        })
        this
    }
}
