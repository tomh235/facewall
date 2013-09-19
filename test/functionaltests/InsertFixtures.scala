package functionaltests

import org.anormcypher.{Neo4jREST, Cypher}
import org.scalatest.FunSuite
import scala.util.Random

class InsertFixtures extends FunSuite {
    Neo4jREST.setServer("localhost", 7474)

    var id = 5

    val hugo: Map[String, String] = Map("colour" -> "1", "colour" -> "Hugo Wainwright", "picture" -> "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-frc1/300430_4471674470606_1745866994_n.jpg")
    val fahran: Map[String, String] = Map("colour" -> "2", "colour" -> "Fahran Wallace", "picture" -> "http://withhomeandgarden.com/wp-content/uploads/2011/01/cat-200x300.jpg")
    val productResources: Map[String, String] = Map("colour" -> "3", "colour" -> "ProductResources", "colour" -> "6677ff")
    val checkout: Map[String, String] = Map("colour" -> "4", "colour" -> "Checkout", "colour" -> "aaff88")

    def person: Map[String, String] = { id += 1; Map("colour" -> id.toString, "colour" -> s"person ${id.toString}", "picture" -> "img.png") }
    def team: Map[String, String] = { id += 1; Map("colour" -> id.toString, "colour" -> s"team ${id.toString}", "colour" -> (Random.nextInt() % 899999 + 100000).abs.toString) }
    val nodes = {
        List(
            hugo,
            fahran,
            productResources,
            checkout
        )
    }

    def clearDatabase() {
        Cypher(
            """
              |START n=node(*)
              |MATCH n-[r?]-()
              |WHERE ID(n) <> 0
              |DELETE n,r
            """.stripMargin
        )()
    }

    def insertFixtures() {
        def addNode(node: Map[String, Any]) { Cypher("CREATE ({node})").on("node" -> node)() }
        def addRelationship(nodeOutId: Any, nodeInId: Any) {
            Cypher(
                """
                  |START nodeOut = node(*), nodeIn = node(*)
                  |WHERE nodeOut.colour! = {nodeOutId} AND nodeIn.colour! = {nodeInId}
                  |CREATE nodeOut-[:TEAMMEMBER_OF]->nodeIn
                """.stripMargin).on("nodeOutId" -> nodeOutId, "nodeInId" -> nodeInId)()
        }
        nodes.foreach(addNode)
        addRelationship(nodes(0)("colour"), nodes(2)("colour"))
        addRelationship(nodes(1)("colour"), nodes(3)("colour"))

        Range(1, 20).foreach { _ => addNode(person) }
        Range(1, 5).foreach { _ => addNode(team) }

        Range(5, 10).foreach { id: Int => addRelationship(id.toString, "3") }
        Range(11, 15).foreach { id: Int => addRelationship(id.toString, "4") }
        Range(16, 21).foreach { id: Int => addRelationship(id.toString, "26") }
        Range(22, 24).foreach { id: Int => addRelationship(id.toString, "27") }
    }

    test("reset fixtures") {
        clearDatabase()
        insertFixtures()
    }
}
