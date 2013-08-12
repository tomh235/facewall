package repository

import org.scalatest.{FunSuite, BeforeAndAfter}
import domain.{Team, Person}
import org.anormcypher.Cypher
import org.neo4j.server.WrappingNeoServerBootstrapper

trait TestGraph {
    private val hugo = Map("id" -> "1", "name" -> "Hugo", "picture" -> "hugo.img")
    private val fahran = Map("id" -> "2", "name" -> "Fahran", "picture" -> "fahran.img")
    private val checkout = Map("id" -> "3", "name" -> "Checkout")
    private val productResources = Map("id" -> "4", "name" -> "ProductResources")

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
        val nodes = List(hugo, fahran, checkout, productResources)
        nodes.foreach(addNode)
        addRelationship(hugo("id"), productResources("id"))
        addRelationship(fahran("id"), checkout("id"))
    }
}

class RepositoryTest extends FunSuite with BeforeAndAfter with TemporaryDatabaseSuite with TestGraph {
    var repo: FacewallRepo = new FacewallRepo()
    var bootstrapper: WrappingNeoServerBootstrapper = _

    val hugo = Person("1", "Hugo", "hugo.img", repo)
    val fahran = Person("2", "Fahran", "fahran.img", repo)
    val checkout = Team("3", "Checkout")
    val productResources = Team("4", "ProductResources")

    before {
        bootstrapper = startNewTestDatabaseRestServerBootstrapper
        setUpGraph()
    }

    after {
        bootstrapper.stop()
    }

    test("listPersons should get Hugo and Fahran") {
        val result = repo.listPersons
        assert(result == List(hugo, fahran), s"expected Hugo and Fahran, got $result")
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
        assert (result == List(fahran), s"expected Fahran, got $result")
    }
}
