package facade.modelmapper

import org.scalatest.FunSuite
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar.mock
import domain.{Team, MockTeam, MockPerson}
import util.CollectionMatcher.contains
import org.hamcrest.MatcherAssert.assertThat
import model.PersonSearchResultMatcher.aPersonSearchResult
import model.TeamSearchResultMatcher.aTeamSearchResult

class SearchResultsModelMapperTest extends FunSuite {
    val searchResultsModelMapper = new SearchResultsModelMapper()

    test("should map persons into person search results") {
        val team = mock[Team]
        when(team.name).thenReturn("a team")

        val fred1 = new MockPerson("1", "fred smith", "pic1.img", Some(team))
        val fred2 = new MockPerson("2", "fred bailey", "pic3.img", Some(team))

        val result = searchResultsModelMapper.map(List(fred1, fred2), Nil)
        assert(result.teams.isEmpty, "should map empty list of teams as empty list")
        assertThat(result.persons, contains(
            aPersonSearchResult.named("fred smith").withPicture("pic1.img").inTeam("a team"),
            aPersonSearchResult.named("fred bailey").withPicture("pic3.img").inTeam("a team")
        ))
    }

    test("should map teams into team search results") {
        val ecom = new MockTeam("1", "Ecom", "blue", Nil)
        val anotherTeam = new MockTeam("2", "another team", "red", Nil)

        val result = searchResultsModelMapper.map(Nil, List(ecom, anotherTeam))
        assertThat(result.teams, contains(
            aTeamSearchResult.named("Ecom"),
            aTeamSearchResult.named("another team")
        ))
    }

    test("should map person's team name as empty string if person is not in a team") {
        val teamless = new MockPerson("1", "teamless", "teamless.img", None)

        val result = searchResultsModelMapper.map(List(teamless), Nil)
        assertThat(result.persons, contains(
            aPersonSearchResult.inTeam("")
        ))
    }
}
