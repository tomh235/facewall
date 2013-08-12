package domain

import org.scalatest.FunSuite
import org.scalamock.scalatest.MockFactory
import repository.Repository

class PersonTest extends FunSuite with MockFactory {
    val mockRepo = mock[Repository]
    val person = Person("id", "hugo", "hugo.img", mockRepo)
    test("getTeam should get team from repository") {
//        mockRepo expects 'findTeamforPerson withArgs (person)
    }
}
