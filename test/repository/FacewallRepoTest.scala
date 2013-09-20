package repository

import org.scalatest.{FunSuite, BeforeAndAfter}
import domain.{Query, MockTeam, MockPerson, Person}
import domain.TeamMatcher.aTeam
import domain.PersonMatcher.aPerson
import org.anormcypher.Cypher
import org.neo4j.server.WrappingNeoServerBootstrapper
import util.CollectionMatcher.contains
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.is
import org.scalatest.mock.MockitoSugar.mock
import org.mockito.Mockito._

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

    val hugo = aPerson.withId("1").named("Hugo").withPicture("person3.img").inTeam(aTeam.named("ProductResources"))
    val fahran = aPerson.withId("2").named("Fahran").withPicture("person3.img").inTeam(aTeam.named("Checkout"))
    val person3 = aPerson.withId("5").named("Person 3").withPicture("Person 3.img").inTeam(aTeam.named("Checkout"))

    val checkout = aTeam.withId("3").named("Checkout").withColour("88aa44").whereMembers(contains(
        aPerson.named("Fahran"), aPerson.named("Person 3")
    ))
    val productResources = aTeam.withId("4").named("ProductResources").withColour("3355ff").whereMembers(contains(
        aPerson.named("Hugo")
    ))

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

    test("queryPersons should find persons matching query") {
        val query = mock[Query]
        when(query.toRegEx).thenReturn("Hugo")
        val result = repo.queryPersons(query)
        assertThat(result, contains(hugo))
    }

    test("queryTeams should find teams matching query") {
        val query = mock[Query]
        when(query.toRegEx).thenReturn(".*Product.*")
        val result = repo.queryTeams(query)
        assertThat(result, contains(productResources))
    }
}
