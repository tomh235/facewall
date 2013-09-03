package facade

import org.scalatest.{BeforeAndAfter, FunSuite}
import model.{OverviewEntry}
import domain.{Person, Team}
import org.scalatest.mock.MockitoSugar.mock
import org.mockito.Mockito.stub
import repository.MockRepository

class FacewallFacadeTest extends FunSuite with BeforeAndAfter {
    val mockRepo = new MockRepository()
    val facewallFacade = new FacewallFacade(mockRepo)

    before {
        mockRepo.clear()
    }

    test("should map from domain objects from repo into team overview model") {
        mockRepo.teams += (Team("1", "ecom", "blue", mockRepo), Team("2", "productResources", "green", mockRepo))
        mockRepo.persons += (
            Person("3", "ecom_member1", "pic1.img", mockRepo),
            Person("4", "ecom_member2", "pic2.img", mockRepo),
            Person("5", "pr_member", "pic3.img", mockRepo)
        )
        mockRepo.relationships += (("3", "1"), ("4", "1"), ("5", "2"))
        val expected = List(
            OverviewEntry("ecom", "ecom_member1", "pic1.img", "blue"),
            OverviewEntry("ecom", "ecom_member2", "pic2.img", "blue"),
            OverviewEntry("productResources", "pr_member", "pic3.img", "green")
        )

        val result = facewallFacade.createOverviewModel
        assert (result == expected, s"expected $expected, got $result")
    }

    test("should order overviews aplphabetically by team with teamless last") {
        mockRepo.teams += (Team("1", "ecom", "blue", mockRepo), Team("2", "productResources", "green", mockRepo))
        mockRepo.persons += (
            Person("3", "ecom_member", "pic1.img", mockRepo),
            Person("4", "pr_member", "pic2.img", mockRepo),
            Person("5", "teamless_member", "pic3.img", mockRepo)
        )
        mockRepo.relationships += (("3", "1"), ("4", "2"))
        val expected = List(
            OverviewEntry("ecom", "ecom_member", "pic1.img", "blue"),
            OverviewEntry("productResources", "pr_member", "pic2.img", "green"),
            OverviewEntry("", "teamless_member", "pic3.img", "")
        )

        val result = facewallFacade.createOverviewModel
        assert (result == expected, s"expected $expected, got $result")
    }
}
