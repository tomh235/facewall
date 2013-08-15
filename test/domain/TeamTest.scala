package domain

import org.scalatest.FunSuite
import repository.Repository
import org.scalatest.mock.MockitoSugar.mock
import org.mockito.Mockito.stub
import org.mockito.Mockito.verify

class TeamTest extends FunSuite {
    val mockRepo = mock[Repository]
    val team = Team("1", "team", "colour", mockRepo)

    test("getMembers should get the list of members from the repository") {
        val expectedList = List(Person("2", "person1", "person1.img", mockRepo), Person("3", "person2", "person2.img", mockRepo))
        stub(mockRepo.findPersonsForTeam(team)).toReturn(expectedList)

        val result = team.getMembers
        assert (result == expectedList, s"expected person1 and person2, got $result")
        verify(mockRepo).findPersonsForTeam(team)
    }
}
