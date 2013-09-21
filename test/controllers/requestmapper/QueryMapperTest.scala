package controllers.requestmapper

import org.scalatest.FunSuite
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar.mock
import domain.Query
import domain.QueryMatcher.aQuery
import play.api.mvc.{AnyContent, AnyContentAsFormUrlEncoded, Request}
import org.hamcrest.{Description, TypeSafeMatcher}
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.is

class QueryMapperTest extends FunSuite {
    val queryMapper = new QueryMapper()

    test("should map from request into query") {
        val request = mock[Request[AnyContent]]
        val queryString = Map("keywords" -> Seq("hello"))
        when(request.queryString).thenReturn(queryString)

        val result = queryMapper.map(request)
        assertThat(result, is(aQuery.withRegEx("(?i).*hello.*")))
    }

    test("should map empty keywords into empty regex") {
        val request = mock[Request[AnyContent]]
        val queryString = Map("keywords" -> Seq(""))
        when(request.queryString).thenReturn(queryString)

        val result = queryMapper.map(request)
        assertThat(result, is(aQuery.withRegEx("")))
    }
}
