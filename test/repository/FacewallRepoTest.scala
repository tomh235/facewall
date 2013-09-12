package repository

import org.scalatest.{FunSuite, BeforeAndAfter}
import domain.{MockTeam, MockPerson, Team, Person}
import org.anormcypher.Cypher
import org.neo4j.server.WrappingNeoServerBootstrapper
import org.hamcrest.{Matcher, Description}
import util.CollectionMatcher.contains
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.is
import org.junit.internal.matchers.TypeSafeMatcher

trait TestGraph {
    private val hugo = Map("id" -> "1", "name" -> "Hugo", "picture" -> "person3.img")
    private val fahran = Map("id" -> "2", "name" -> "Fahran", "picture" -> "person3.img")
    private val person3 = Map("id" -> "5", "name" -> "Person 3", "picture" -> "Person 3.img")
    private val checkout = Map("id" -> "3", "name" -> "Checkout", "colour" -> "88aa44")
    private val productResources = Map("id" -> "4", "name" -> "ProductResources", "colour" -> "3355ff")

    def setUpGraph() {
        def addNode(node: Map[String, Any]) {
            Cypher("CREATE ({node})").on("node" -> node)()
        }
        def addRelationship(nodeOutId: Any, nodeInId: Any) {
            Cypher(
                """
                  |START nodeOut = node(*), nodeIn = node(*)
                  |WHERE nodeOut.id! = {nodeOutId} AND nodeIn.id! = {nodeInId}
                  |CREATE nodeOut-[:TEAMMEMBER_OF]->nodeIn
                """.stripMargin).on("nodeOutId" -> nodeOutId, "nodeInId" -> nodeInId)()
        }
        val nodes = List(hugo, fahran, person3, checkout, productResources)
        nodes.foreach(addNode)
        addRelationship(hugo("id"), productResources("id"))
        addRelationship(fahran("id"), checkout("id"))
        addRelationship(person3("id"), checkout("id"))
    }
}

class FacewallRepoTest extends FunSuite with BeforeAndAfter with TemporaryDatabaseSuite with TestGraph {
    var repo: FacewallRepo = new FacewallRepo()
    var bootstrapper: WrappingNeoServerBootstrapper = _

    val hugo: Matcher[Person] = new TypeSafeMatcher[Person]() {
        def matchesSafely(hugo: Person): Boolean =
            hugo.id == "1" &&
                hugo.name == "Hugo" &&
                hugo.picture == "person3.img" &&
                hugo.team.fold(false) {
                    team => team.name == "ProductResources"
                }

        def describeTo(description: Description) {
            description.appendText("should be Hugo")
        }
    }

    val fahran: Matcher[Person] = new TypeSafeMatcher[Person]() {
        def matchesSafely(fahran: Person): Boolean =
            fahran.id == "2" &&
                fahran.name == "Fahran" &&
                fahran.picture == "person3.img" &&
                fahran.team.fold(false) {
                    team => team.name == "Checkout"
                }

        def describeTo(description: Description) {
            description.appendText("should be Fahran")
        }
    }

    val person3: Matcher[Person] = new TypeSafeMatcher[Person]() {
        def matchesSafely(person3: Person): Boolean =
            person3.id == "5" &&
                person3.name == "Person 3" &&
                person3.picture == "Person 3.img" &&
                person3.team.fold(false) {
                    team => team.name == "Checkout"
                }

        def describeTo(description: Description) {
            description.appendText("should be Person 3")
        }
    }

    val checkout = new TypeSafeMatcher[Team]() {
        def matchesSafely(checkout: Team): Boolean =
            checkout.id == "3" &&
                checkout.name == "Checkout" &&
                checkout.colour == "88aa44"

        def describeTo(description: Description) {
            description.appendText("should be Checkout")
        }
    }

    val productResources = new TypeSafeMatcher[Team]() {
        def matchesSafely(checkout: Team): Boolean =
            checkout.id == "4" &&
                checkout.name == "ProductResources" &&
                checkout.colour == "3355ff"

        def describeTo(description: Description) {
            description.appendText("should be ProductResources")
        }
    }

    before {
        bootstrapper = startNewTestDatabaseRestServerBootstrapper
        setUpGraph()
    }

    after {
        bootstrapper.stop()
    }

    test("listPersons should get all persons") {
        val result = repo.listPersons
        assertThat(result, contains(hugo, fahran, person3))
    }

    test("findTeamForPerson should find Team that Person is member of") {
        val result = repo.findTeamForPerson(new MockPerson("1", "hugo", "hugo.img")).get
        assertThat(result, is(productResources))
    }

    test("listTeams should get Checkout and ProductResources") {
        val result = repo.listTeams
        assertThat(result, contains(checkout, productResources))
    }

    test("findPersonsForTeam should find Persons that are members of the Team") {
        val result = repo.findPersonsForTeam(MockTeam("3", "Checkout", "blue", List.empty[Person]))
        assertThat(result, contains(fahran, person3))
    }
}
