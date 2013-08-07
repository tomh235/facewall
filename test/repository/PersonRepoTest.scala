package repository

import org.scalatest.BeforeAndAfter
import domain.Person
import org.anormcypher.Cypher

trait TestGraph {
    val hugo = Map("name" -> "Hugo", "picture" -> "hugo.img")
    val fahran = Map("name" -> "Fahran", "picture" -> "fahran.img")

    def setUpGraph() {
        Cypher(
            """
              |START n=node(*)
              |MATCH n-[r?]-()
              |WHERE ID(n) <> 0
              |DELETE n,r
            """.stripMargin
        )()
        Cypher(s"CREATE (n {person}) RETURN n").on("person" -> hugo)()
        Cypher(s"CREATE (n {person}) RETURN n").on("person" -> fahran)()
    }
}

class PersonRepoTest extends TemporaryDatabaseSuite with TestGraph with BeforeAndAfter {
    var repo: PersonRepo = _

    test("getEveryone should get Hugo and Fahran") {
        setUpGraph()
        val hugo = Person("Hugo", "hugo.img")
        val fahran = Person("Fahran", "fahran.img")
        repo = PersonRepo()

        val result = repo.getEveryone
        assert(result == List(hugo, fahran), s"Hugo and Fahran should have been everyone, got $result")
    }
}
