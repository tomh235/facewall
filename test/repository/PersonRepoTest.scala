package repository

import org.scalatest.{FunSuite, BeforeAndAfter}
import domain.Person
import org.anormcypher.{Neo4jREST, Cypher}

trait TestGraph {
    val hugo = Map("name" -> "Hugo", "picture" -> "hugo.img")
    val fahran = Map("name" -> "Fahran", "picture" -> "fahran.img")

    def setUpGraph() {
        Cypher(s"CREATE (n {person}) RETURN n").on("person" -> hugo)()
        Cypher(s"CREATE (n {person}) RETURN n").on("person" -> fahran)()
    }
}

class PersonRepoTest extends FunSuite with TemporaryDatabase with TestGraph with BeforeAndAfter {
    var repo: PersonRepo = _

    before {
        start()
    }

    after {
        stop()
    }

    test("getEveryone should get Hugo and Fahran") {
        Neo4jREST.setServer("localhost", port)
        setUpGraph()
        val hugo = Person("Hugo", "hugo.img")
        val fahran = Person("Fahran", "fahran.img")
        repo = PersonRepo(port)

        val result = repo.getEveryone
        assert(result == List(hugo, fahran), s"Hugo and Fahran should have been everyone, got $result")
    }
}
