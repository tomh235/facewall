package repository

import org.scalatest.{FunSuite, BeforeAndAfter}
import domain.{Team, Person}
import org.anormcypher.Cypher
import org.neo4j.server.WrappingNeoServerBootstrapper

trait TestGraph {
    private val hugo = Map("id" -> "1", "name" -> "Hugo", "picture" -> "hugo.img")
    private val fahran = Map("id" -> "2", "name" -> "Fahran", "picture" -> "fahran.img")
    private val person3 = Map("id" -> "5", "name" -> "Person 3", "picture" -> "Person 3.img")
    private val checkout = Map("id" -> "3", "name" -> "Checkout", "colour" -> "88aa44")
    private val productResources = Map("id" -> "4", "name" -> "ProductResources", "colour" -> "3355ff")

    def setUpGraph() {
        def addNode(node: Map[String, Any]) { Cypher("CREATE ({node})").on("node" -> node)() }
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

    val hugo = Person("1", "Hugo", "hugo.img", repo)
    val fahran = Person("2", "Fahran", "fahran.img", repo)
    val person3 = Person("5", "Person 3", "Person 3.img", repo)
    val checkout = Team("3", "Checkout", "88aa44", repo)
    val productResources = Team("4", "ProductResources", "3355ff", repo)

    before {
        bootstrapper = startNewTestDatabaseRestServerBootstrapper
        setUpGraph()
    }

    after {
        bootstrapper.stop()
    }

    test("listPersons should get all persons") {
        val result = repo.listPersons
        assert(result == List(hugo, fahran, person3), s"expected Hugo, Fahran and Person 3, got $result")
    }

    test("findTeamForPerson should find Team that Person is member of") {
        val result = repo.findTeamForPerson(hugo)
        assert(result == Some(productResources), s"expected ProductResources, got $result")
    }

    test("listTeams should get Checkout and ProductResources") {
        val result = repo.listTeams
        assert (result == List(checkout, productResources), s"expected Checkout and ProductResources, got $result")
    }

    test("findPersonsForTeam should find Persons that are members of the Team") {
        val result = repo.findPersonsForTeam(checkout)
        assert (result == List(fahran, person3), s"expected Fahran and Person 3, got $result")
    }
}
