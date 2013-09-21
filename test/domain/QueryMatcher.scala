package domain

import util.CompositeMatcher
import org.hamcrest.{Description, TypeSafeMatcher}

object QueryMatcher {
    def aQuery = new QueryMatcher()
}

class QueryMatcher extends CompositeMatcher[Query] {
    val typeName = "a Query"

    def withRegEx(regex: String) = {
        add(new TypeSafeMatcher[Query]() {
            def matchesSafely(query: Query): Boolean = query.toRegEx == regex


            def describeTo(description: Description) {
                description.appendText(s"where the regex is $regex")
            }
        })
        this
    }
}
