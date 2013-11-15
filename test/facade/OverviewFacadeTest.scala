package facade

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.mockito.Mockito._
import model.OverviewEntryModel
import domain._
import org.scalatest.mock.MockitoSugar.mock
import repository.Repository
import domain.MockTeam

import scala.collection.JavaConverters._


class OverviewFacadeTest extends FunSuite with BeforeAndAfter {
    val mockRepo = mock[Repository]
    val facewallFacade = new OverviewFacade(mockRepo)

    test("should map from domain objects from repo into team overview model") {
        val ecom_member1 = new MockPerson("3", "ecom_member1", "pic1.img", null)
        val ecom_member2 = new MockPerson("4", "ecom_member2", "pic2.img", null)
        val pr_member = new MockPerson("5", "pr_member", "pic3.img", null)


        val ecom: Team = new MockTeam("1", "ecom", "blue", List(ecom_member1, ecom_member2).asInstanceOf[List[Person]].asJava)
        val productResources: Team = new MockTeam("2", "productResources", "green", List(pr_member).asInstanceOf[List[Person]].asJava)

        ecom_member1.setTeam(ecom)
        ecom_member2.setTeam(ecom)
        pr_member.setTeam(productResources)

        val persons = List(ecom_member1, ecom_member2, pr_member).asInstanceOf[List[Person]].asJava
        when(mockRepo.listPersons).thenReturn(persons)

        val expected = List(
            OverviewEntryModel("ecom", "ecom_member1", "pic1.img", "blue"),
            OverviewEntryModel("ecom", "ecom_member2", "pic2.img", "blue"),
            OverviewEntryModel("productResources", "pr_member", "pic3.img", "green")
        ).asJava

        val result = facewallFacade.createOverviewModel
        assert (result == expected, s"expected $expected, got $result")
    }

    test("should order overviews alphabetically by team with teamless last") {
        val ecom_member = new MockPerson("3", "ecom_member", "pic1.img", null)
        val pr_member = new MockPerson("4", "pr_member", "pic2.img", null)
        val teamless_member = new MockPerson("5", "teamless_member", "pic3.img", null)

        // casts types of List[MockPerson] to type List[Person]
        val ecom: Team = new MockTeam("1", "ecom", "blue", List(ecom_member).asInstanceOf[List[Person]].asJava)
        val productResources: Team = new MockTeam("2", "productResources", "green", List(pr_member).asInstanceOf[List[Person]].asJava)
        val noTeam: Team = new MockTeam("3", "", "grey", List(teamless_member).asInstanceOf[List[Person]].asJava)

        ecom_member.setTeam(ecom)
        pr_member.setTeam(productResources)
        teamless_member.setTeam(noTeam)

        val persons = List(teamless_member, pr_member, ecom_member).asInstanceOf[List[Person]].asJava
        when(mockRepo.listPersons).thenReturn(persons)

        val expected = List(
            OverviewEntryModel("ecom", "ecom_member", "pic1.img", "blue"),
            OverviewEntryModel("productResources", "pr_member", "pic2.img", "green"),
            OverviewEntryModel("", "teamless_member", "pic3.img", "grey")
        ).asJava

        val result = facewallFacade.createOverviewModel
        assert (result == expected, s"expected $expected, got $result")
    }
}
