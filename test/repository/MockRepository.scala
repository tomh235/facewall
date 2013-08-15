package repository

import domain.{Person, Team}
import org.scalatest.{BeforeAndAfter, FunSuite}
import scala.collection.mutable
import scala.Predef._
import domain.Person
import domain.Team

class MockRepository(
    val teams: mutable.MutableList[Team] = mutable.MutableList[Team](),
    val persons: mutable.MutableList[Person] = mutable.MutableList[Person](),
    val relationships: mutable.MutableList[Pair[String, String]] = mutable.MutableList[Pair[String, String]]()
) extends Repository {
    def findTeamForPerson(person: Person): Option[Team] = {
        val teamId = relationships.find { _._1 == person.id }.getOrElse(return None)._2
        teams.find { _.id == teamId }
    }

    def listPersons: List[Person] = persons.toList

    def listTeams: List[Team] = teams.toList

    def findPersonsForTeam(team: Team): List[Person] = {
        val personIds = relationships.filter { _._2 == team.id }.map { _._1 }
        personIds.flatMap { id => persons.find { _.id == id } }
    }.toList

    def clear() {
        persons.clear()
        teams.clear()
        relationships.clear()
    }
}

class MockRepositoryTest extends FunSuite  with BeforeAndAfter {
    private val teamList = mutable.MutableList[Team]()
    private val personList = mutable.MutableList[Person]()
    private val relationships = mutable.MutableList[(String, String)]()
    private val mockRepo: MockRepository = new MockRepository(teamList, personList, relationships)

    before {
        teamList.clear()
        personList.clear()
        relationships.clear()
    }

    test("findTeamForPerson should work with stubbed data") {
        val bob: Person = Person("2", "bob", "bob.img", mockRepo)
        val ecom = Team("1", "ecom", "blue", mockRepo)
        personList += bob
        teamList += ecom
        relationships += Pair("2", "1")

        val result = mockRepo.findTeamForPerson(bob)
        assert(result == Some(ecom), s"expected ${Some(ecom)}, got $result")
    }

    test("findPersonForTeam should work with stubbed data") {
        val persons = List(Person("1", "person", "image", mockRepo), Person("2", "pers", "img", mockRepo), Person("3", "bob", "red.img", mockRepo))
        val ecom = Team("4", "ecom", "blue", mockRepo)
        persons.foreach(personList+=)
        teamList += ecom
        relationships += (Pair("1", "4"), Pair("2", "4"), Pair("3", "4"))

        val result = mockRepo.findPersonsForTeam(ecom)
        assert(result == persons, s"expected $persons, got $result")
    }

    test("listTeams should return stubbed list of teams") {
        val teams = List(Team("1", "ecom", "blue", mockRepo), Team("2", "anr", "green", mockRepo), Team("3", "4g", "red", mockRepo))
        teams.foreach(teamList+=)

        val result = mockRepo.listTeams
        assert(result == teams, s"expected $teams, got $result")
    }

    test("listPersons should return stubbed list of persons") {
        val persons = List(Person("1", "person", "image", mockRepo), Person("2", "pers", "img", mockRepo), Person("3", "bob", "red.img", mockRepo))
        persons.foreach(personList+=)

        val result: List[Person] = mockRepo.listPersons
        assert(result == persons, s"expected $persons, got $result")
    }
}