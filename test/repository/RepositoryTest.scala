package repository

import org.scalatest.{FunSuite, BeforeAndAfter}
import domain.{Team, Person}
import org.anormcypher.Cypher
import org.neo4j.server.WrappingNeoServerBootstrapper

trait TestGraph {
    val hugo = Map("id" -> "1", "name" -> "Hugo", "picture" -> "hugo.img")
    val fahran = Map("id" -> "2", "name" -> "Fahran", "picture" -> "fahran.img")
    val checkout = Map("id" -> "3", "name" -> "Checkout")
    val productResources = Map("id" -> "4", "name" -> "ProductResources")

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
    var repo: Repository = _
    var bootstrapper: WrappingNeoServerBootstrapper = _

    before {
        bootstrapper = startNewTestDatabaseRestServerBootstrapper
        setUpGraph()
    }

    after {
        bootstrapper.stop()
    }

    test("listPersons should get Hugo and Fahran") {
        val hugo = Person("1", "Hugo", "hugo.img")
        val fahran = Person("2", "Fahran", "fahran.img")
        repo = Repository()

        val result = repo.listPersons
        assert(result == List(hugo, fahran), s"expected Hugo and Fahran, got $result")
    }

    test("findTeam should find Team that Person is member of") {
        val hugo = Person("1", "Hugo", "hugo.img")
        val productResources = Team("4", "ProductResources")
        repo = Repository()

        val result = repo.findTeamForPerson(hugo)
        assert(result == Some(productResources), s"expected ProductResources, got $result")
    }
}
