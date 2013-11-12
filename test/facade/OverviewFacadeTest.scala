package facade

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.mockito.Mockito._
import model.OverviewEntry
import domain._
import org.scalatest.mock.MockitoSugar.mock
import repository.Repository
import domain.MockTeam

import scala.collection.JavaConverters._


class OverviewFacadeTest extends FunSuite with BeforeAndAfter {
    val mockRepo = mock[Repository]
    val facewallFacade = new OverviewFacade(mockRepo)

    test("should map from domain objects from repo into team overview model") {
        val ecom_member1 = new MockPerson("3", "ecom_member1", "pic1.img")
        val ecom_member2 = new MockPerson("4", "ecom_member2", "pic2.img")
        val pr_member = new MockPerson("5", "pr_member", "pic3.img")


        val ecom: Team = new MockTeam("1", "ecom", "blue", List(ecom_member1, ecom_member2).asInstanceOf[List[Person]].asJava)
        val productResources: Team = new MockTeam("2", "productResources", "green", List(pr_member).asInstanceOf[List[Person]].asJava)

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

    test("should order overviews alphabetically by team with teamless last") {
        val ecom_member = new MockPerson("3", "ecom_member", "pic1.img")
        val pr_member = new MockPerson("4", "pr_member", "pic2.img")
        val teamless_member = new MockPerson("5", "teamless_member", "pic3.img")

        // casts types of List[MockPerson] to type List[Person]
        val ecom: Team = new MockTeam("1", "ecom", "blue", List(ecom_member).asInstanceOf[List[Person]].asJava)
        val productResources: Team = new MockTeam("2", "productResources", "green", List(pr_member).asInstanceOf[List[Person]].asJava)

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
}
