package domain

import org.scalatest.FunSuite
import repository.Repository
import org.scalatest.mock.MockitoSugar.mock
import org.mockito.Mockito.stub
import org.mockito.Mockito.verify

class PersonTest extends FunSuite {
    val mockRepo = mock[Repository]
    val person = Person("1", "hugo", "hugo.img", mockRepo)
    test("getTeam should get team from repository") {
        val expected = Some(Team("2", "team", "colour", mockRepo))
        stub(mockRepo.findTeamForPerson(person)).toReturn(expected)

        val result = person.getTeam
        verify(mockRepo).findTeamForPerson(person)
        assert (result == expected, s"expected team, got $result")
    }
}
