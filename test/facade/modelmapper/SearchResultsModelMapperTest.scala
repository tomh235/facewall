package facade.modelmapper

import org.scalatest.FunSuite
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar.mock
import domain.{MockPerson, Person, Team, MockTeam}
import util.CollectionMatcher.contains
import org.hamcrest.MatcherAssert.assertThat
import model.PersonSearchResultMatcher.aPersonSearchResult
import model.TeamSearchResultMatcher.aTeamSearchResult

import scala.collection.JavaConverters._

class SearchResultsModelMapperTest extends FunSuite {
    val searchResultsModelMapper = new SearchResultsModelMapper()

    test("should map persons into person search results") {
        val team = mock[Team]
        when(team.name).thenReturn("a team")

        val fred1 : Person = new MockPerson("1", "fred smith", "pic1.img", team)
        val fred2 : Person = new MockPerson("2", "fred bailey", "pic3.img", team)

        val result = searchResultsModelMapper.map(List(fred1, fred2).asJava, List.empty[Team].asJava)
        assert(result.teams.isEmpty, "should map empty list of teams as empty list")
        assertThat(result.persons, contains(
            aPersonSearchResult.named("fred smith").withPicture("pic1.img").inTeam("a team"),
            aPersonSearchResult.named("fred bailey").withPicture("pic3.img").inTeam("a team")
        ))
    }

    test("should map teams into team search results") {
        val ecom : Team= new MockTeam("1", "Ecom", "blue", List.empty[Person].asJava)
        val anotherTeam : Team = new MockTeam("2", "another team", "red", List.empty[Person].asJava)

        val result = searchResultsModelMapper.map(List.empty[Person].asJava, List(ecom, anotherTeam).asJava)
        assertThat(result.teams, contains(
            aTeamSearchResult.named("Ecom"),
            aTeamSearchResult.named("another team")
        ))
    }

    test("should map person's team name as empty string if person is not in a team") {
        val team = mock[Team]
        when(team.name).thenReturn("")

        val teamless : Person = new MockPerson("1", "teamless", "teamless.img", team)

        val result = searchResultsModelMapper.map(List(teamless).asJava, List.empty[Team].asJava)
        assertThat(result.persons, contains(
            aPersonSearchResult.inTeam("")
        ))
    }
}
