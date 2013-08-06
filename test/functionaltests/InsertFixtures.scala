package functionaltests

import org.anormcypher.{Neo4jREST, Cypher}
import org.scalatest.FunSuite

class InsertFixtures extends FunSuite {
    Neo4jREST.setServer("localhost", 7474)

    val people = List(
        Map("name" -> "Hugo", "picture" -> "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-frc1/300430_4471674470606_1745866994_n.jpg"),
        Map("name" -> "Fahran", "picture" -> "http://withhomeandgarden.com/wp-content/uploads/2011/01/cat-200x300.jpg")
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
        people.foreach { person => Cypher("CREATE ({person})").on("person" -> person)() }
    }

    test("reset fixtures") {
        clearDatabase()
        insertFixtures()
    }
}
