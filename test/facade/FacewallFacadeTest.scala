package facade

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.mockito.Mockito._
import model.{PersonSearchResultMatcher, OverviewEntry}
import model.PersonSearchResultMatcher.aPersonSearchResult
import domain.{MockTeam, MockPerson, Team, Query}
import org.scalatest.mock.MockitoSugar.mock
import repository.Repository
import util.CollectionMatcher.contains
import org.hamcrest.MatcherAssert.assertThat

class FacewallFacadeTest extends FunSuite with BeforeAndAfter {
    val mockRepo = mock[Repository]
    val facewallFacade = new FacewallFacade(mockRepo)

    test("should map from domain objects from repo into team overview model") {


        val ecom_member1 = new MockPerson("3", "ecom_member1", "pic1.img")
        val ecom_member2 = new MockPerson("4", "ecom_member2", "pic2.img")
        val pr_member = new MockPerson("5", "pr_member", "pic3.img")

        val ecom: Team = MockTeam("1", "ecom", "blue", List(ecom_member1, ecom_member2))
        val productResources: Team = MockTeam("2", "productResources", "green", List(pr_member))

        ecom_member1.setTeam(ecom)
        ecom_member2.setTeam(ecom)
        pr_member.setTeam(productResources)

        val persons = List(ecom_member1, ecom_member2, pr_member)
        when(mockRepo.listPersons).thenReturn(persons)

        val expected = List(
            OverviewEntry("ecom", "ecom_member1", "pic1.img", "blue"),
            OverviewEntry("ecom", "ecom_member2", "pic2.img", "blue"),
            OverviewEntry("productResources", "pr_member", "pic3.img", "green")
        )

        val result = facewallFacade.createOverviewModel
        assert (result == expected, s"expected $expected, got $result")
    }

    test("should order overviews aplphabetically by team with teamless last") {
        val ecom_member = new MockPerson("3", "ecom_member", "pic1.img")
        val pr_member = new MockPerson("4", "pr_member", "pic2.img")
        val teamless_member = new MockPerson("5", "teamless_member", "pic3.img")

        val ecom: Team = MockTeam("1", "ecom", "blue", List(ecom_member))
        val productResources: Team = MockTeam("2", "productResources", "green", List(pr_member))

        ecom_member.setTeam(ecom)
        pr_member.setTeam(productResources)

        val persons = List(teamless_member, pr_member, ecom_member)
        when(mockRepo.listPersons).thenReturn(persons)

        val expected = List(
            OverviewEntry("ecom", "ecom_member", "pic1.img", "blue"),
            OverviewEntry("productResources", "pr_member", "pic2.img", "green"),
            OverviewEntry("", "teamless_member", "pic3.img", "")
        )

        val result = facewallFacade.createOverviewModel
        assert (result == expected, s"expected $expected, got $result")
    }

    test("should find persons and teams matching a query extracted from a web request and map them into a search results model") {
        val query = mock[Query]
        val fred1 = new MockPerson("1", "fred smith", "pic1.img")
        val fred2 = new MockPerson("2", "fred bailey", "pic3.img")

        val frederationMember = new MockPerson("3", "frederation member", "img")
        val frederation = new MockTeam("4", "frederation", "blue", List(frederationMember))

        val ecom = new MockTeam("5", "ecom", "teal", List(fred1, fred2))

        fred1.setTeam(ecom)
        fred2.setTeam(ecom)

        when(mockRepo.queryPersons(query)).thenReturn(List(fred1, fred2))
        when(mockRepo.queryTeams(query)).thenReturn(List(frederation))

        val result = facewallFacade.createSearchResultsModel(query)

        assertThat(result.persons, contains(
            aPersonSearchResult.named("fred smith"),
            aPersonSearchResult.named("fred bailey")
        ))
    }
}
