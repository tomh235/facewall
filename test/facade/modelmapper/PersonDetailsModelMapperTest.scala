package facade.modelmapper

import org.hamcrest.MatcherAssert.assertThat
import model.PersonDetailsModelMatcher.aPersonDetailsModel
import org.hamcrest.Matchers.is
import org.scalatest.FunSuite
import domain.MockPerson

class PersonDetailsModelMapperTest extends FunSuite {
    val personDetailsModelMapper = new PersonDetailsModelMapper()

    test("should map person") {
        val person = new MockPerson("id", "Hello World", "picture")
        val result = personDetailsModelMapper.map(person)

        assertThat(result, is(aPersonDetailsModel.named("Hello World")))
    }
}
