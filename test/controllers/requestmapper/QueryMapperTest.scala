package controllers.requestmapper

import org.scalatest.FunSuite
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar.mock
import domain.Query
import play.api.mvc.{AnyContent, AnyContentAsFormUrlEncoded, Request}
import org.hamcrest.{Description, TypeSafeMatcher}
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.is

class QueryMapperTest extends FunSuite {
    val queryMapper = new QueryMapper()

    test("should map from request into query") {
        val request = mock[Request[AnyContent]]
        val body = AnyContentAsFormUrlEncoded(Map("keywords" -> List("hello")))
        when(request.body).thenReturn(body)

        val result = queryMapper.map(request)
        val expectedQuery = new TypeSafeMatcher[Query]() {
            def matchesSafely(target: Query): Boolean = target.toRegEx == "hello"

            def describeTo(description: Description) { description.appendText("regEx should be 'hello'") }
        }

        assertThat(result, is(expectedQuery))
    }
}
