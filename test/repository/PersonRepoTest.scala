package repository

import org.scalatest.{BeforeAndAfter, FunSuite}
import domain.Person
import org.anormcypher.{Cypher, Neo4jREST}

trait TestGraph {
  val hugo = Map("name" -> "Hugo", "picture" -> "hugo.img")
  val fahran = Map("name" -> "Fahran", "picture" -> "fahran.img")

  def setUpGraph() {
    Neo4jREST.setServer("localhost", 7474)
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

class PersonRepoTest extends FunSuite with TestGraph with BeforeAndAfter {
  var repo: PersonRepo = _

  before {
      setUpGraph()
  }

  test("getEveryone should get Hugo and Fahran") {
    val hugo = Person("Hugo", "hugo.img")
    val fahran = Person("Fahran", "fahran.img")
    repo = PersonRepo()

    val result = repo.getEveryone
    assert(result == List(hugo, fahran), s"Hugo and Fahran should have been everyone, got $result")
  }
}
