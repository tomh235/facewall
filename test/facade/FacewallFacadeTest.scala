package facade

import org.scalatest.FunSuite
import model.{PersonOverview, TeamOverview}
import domain.{Person, Team}
import org.scalatest.mock.MockitoSugar.mock
import org.mockito.Mockito.stub

class FacewallFacadeTest extends FunSuite {
    val facewallFacade = new FacewallFacade()

    //TODO: this test could be clearer
    test("should map from domain.Teams into team overview model") {
        val mockTeam = mock[Team]
        val mockPerson = mock[Person]
        stub(mockTeam.getMembers)
            .toReturn(List(mockPerson, mockPerson))
            .toReturn(List(mockPerson))

        stub(mockTeam.name)
            .toReturn("team1")
            .toReturn("team2")

        stub(mockPerson.name)
            .toReturn("person1")
            .toReturn("person2")
            .toReturn("person3")

        stub(mockPerson.picture)
            .toReturn("person1 pic")
            .toReturn("person2 pic")
            .toReturn("person3 pic")

        val expected = List(
            TeamOverview(
                "team1",
                List(PersonOverview("person1", "person1 pic"), PersonOverview("person2", "person2 pic"))
            ),
            TeamOverview(
                "team2",
                List(PersonOverview("person3", "person3 pic"))
            )
        )

        val result = facewallFacade.mapTeamOverviewModel(List(mockTeam, mockTeam))
        assert (result == expected, s"expected $expected, got $result")
    }
}
