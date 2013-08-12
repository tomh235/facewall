package functionaltests

import org.anormcypher.{Neo4jREST, Cypher}
import org.scalatest.FunSuite

class InsertFixtures extends FunSuite {
    Neo4jREST.setServer("localhost", 7474)

    val nodes = List(
        Map("id" -> "1", "name" -> "Hugo Wainwright", "picture" -> "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-frc1/300430_4471674470606_1745866994_n.jpg"),
        Map("id" -> "2", "name" -> "Fahran Wallace", "picture" -> "http://withhomeandgarden.com/wp-content/uploads/2011/01/cat-200x300.jpg"),
        Map("id" -> "3", "name" -> "ProductResources"),
        Map("id" -> "4", "name" -> "Checkout")
    )

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
                  |WHERE nodeOut.id! = {nodeOutId} AND nodeIn.id! = {nodeInId}
                  |CREATE nodeOut-[:TEAMMEMBER_OF]->nodeIn
                """.stripMargin).on("nodeOutId" -> nodeOutId, "nodeInId" -> nodeInId)()
        }

        nodes.foreach(addNode)
        addRelationship(nodes(0)("id"), nodes(2)("id"))
        addRelationship(nodes(1)("id"), nodes(3)("id"))
    }

    test("reset fixtures") {
        clearDatabase()
        insertFixtures()
    }
}
